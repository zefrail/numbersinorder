package zeev.fraiman.numbersinorder;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

// Стартовая активность, отображаемая 6 секунд с анимацией названия и TextView
public class Start extends AppCompatActivity {

    private TextView titleTextView;
    private TextView animatedTextView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Инициализация UI-элементов
        titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setAlpha(0);
        animatedTextView = findViewById(R.id.animatedTextView);

        titleTextView.animate().alpha(1f).setDuration(5000).start();

        // Получение ширины экрана для анимации TextView
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int targetWidth = (int) (screenWidth * 0.8); // 80% ширины экрана

        // Анимация увеличения ширины TextView за 5 секунд
        ObjectAnimator widthAnimator = ObjectAnimator.ofInt(animatedTextView, "width", 0, targetWidth);
        widthAnimator.setDuration(5000); // 5 секунд
        widthAnimator.addUpdateListener(animation -> {
            int newWidth = (int) animation.getAnimatedValue();
            animatedTextView.getLayoutParams().width = newWidth;
            animatedTextView.requestLayout();
        });

        // Анимация смены цвета текста TextView (от жёлтого к зелёному)
        ObjectAnimator colorAnimator = ObjectAnimator.ofObject(
                animatedTextView,
                "textColor",
                new ArgbEvaluator(),
                0xFFFFEB3B, // Жёлтый
                0xFF4CAF50  // Зелёный
        );
        colorAnimator.setDuration(5000); // 5 секунд

        // Запуск анимаций
        widthAnimator.start();
        colorAnimator.start();

        // Переход к SettingsActivity через 6 секунд
        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            startActivity(new Intent(Start.this, SettingsActivity.class));
            finish(); // Закрываем SplashActivity
        }, 7000); // 7 секунд
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Очистка Handler для предотвращения утечек памяти
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}

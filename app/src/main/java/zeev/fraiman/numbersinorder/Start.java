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

// Start activity, displayed for 6 seconds with title and TextView animation
public class Start extends AppCompatActivity {

    private TextView titleTextView;
    private TextView animatedTextView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Initializing UI elements
        titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setAlpha(0);
        animatedTextView = findViewById(R.id.animatedTextView);

        titleTextView.animate().alpha(1f).setDuration(5000).start();

        // Getting screen width for TextView animation
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int targetWidth = (int) (screenWidth * 0.8); // 80% of screen width

        // Animation for increasing TextView width over 5 seconds
        ObjectAnimator widthAnimator = ObjectAnimator.ofInt(animatedTextView, "width", 0, targetWidth);
        widthAnimator.setDuration(5000); // 5 seconds
        widthAnimator.addUpdateListener(animation -> {
            int newWidth = (int) animation.getAnimatedValue();
            animatedTextView.getLayoutParams().width = newWidth;
            animatedTextView.requestLayout();
        });

        // Animation for changing TextView text color (from yellow to green)
        ObjectAnimator colorAnimator = ObjectAnimator.ofObject(
                animatedTextView,
                "textColor",
                new ArgbEvaluator(),
                0xFFFFEB3B, // Yellow
                0xFF4CAF50  // Green
        );
        colorAnimator.setDuration(5000); // 5 seconds

        // Starting animations
        widthAnimator.start();
        colorAnimator.start();

        // Transition to SettingsActivity after 7 seconds
        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            startActivity(new Intent(Start.this, SettingsActivity.class));
            finish(); // Closing SplashActivity
        }, 7000); // 7 seconds
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clearing the Handler to prevent memory leaks
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}

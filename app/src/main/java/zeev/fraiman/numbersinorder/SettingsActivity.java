package zeev.fraiman.numbersinorder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Активность для выбора режима игры (ограниченные попытки или без ограничений)
public class SettingsActivity extends AppCompatActivity {
    private RadioGroup modeRadioGroup;
    private RadioButton unlimitedModeRadio, limitedModeRadio;
    private EditText attemptsInput;
    private Button startButton;
    private boolean isUnlimited;
    private int attempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Инициализация UI-элементов
        modeRadioGroup = findViewById(R.id.modeRadioGroup);
        unlimitedModeRadio = findViewById(R.id.unlimitedModeRadio);
        limitedModeRadio = findViewById(R.id.limitedModeRadio);
        attemptsInput = findViewById(R.id.attemptsInput);
        startButton = findViewById(R.id.startButton);

        // Загрузка сохранённых настроек
        SharedPreferences prefs = getSharedPreferences("GameSettings", MODE_PRIVATE);
        isUnlimited = prefs.getBoolean("unlimitedMode", true);
        attempts = prefs.getInt("attempts", 5);
        unlimitedModeRadio.setChecked(isUnlimited);
        limitedModeRadio.setChecked(!isUnlimited);
        attemptsInput.setText(String.valueOf(attempts));
        attemptsInput.setEnabled(!isUnlimited);

        // Обработчик выбора режима
        modeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            attemptsInput.setEnabled(checkedId == R.id.limitedModeRadio);
        });

        // Обработчик кнопки "Начать"
        startButton.setOnClickListener(v -> {
            isUnlimited = unlimitedModeRadio.isChecked();
            attempts = 5;
            if (!isUnlimited) {
                try {
                    attempts = Integer.parseInt(attemptsInput.getText().toString());
                    if (attempts <= 0) {
                        Toast.makeText(this, "Введите число попыток больше 0!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Введите корректное число попыток!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Сохранение настроек
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("unlimitedMode", isUnlimited);
            editor.putInt("attempts", attempts);
            editor.apply();

            // Переход к игре
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
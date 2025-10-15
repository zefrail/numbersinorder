package zeev.fraiman.numbersinorder;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

// Main activity of the game, where the core logic of ordering numbers happens
public class MainActivity extends AppCompatActivity {
    private Button[] numberButtons = new Button[5]; // Buttons to display numbers
    private TextView[] numberTextViews = new TextView[5]; // TextViews to display "?" or numbers
    private ImageView[] starViews = new ImageView[5]; // ImageViews for progress stars
    private ArrayList<Integer> numbers = new ArrayList<>(); // List of random numbers
    private int currentIndex = 0; // Current index of the expected number
    private int attemptsLeft; // Remaining attempts (if in limited mode)
    private boolean isUnlimitedMode; // Flag for unlimited mode
    private SoundPool soundPool; // For playing sounds
    private int successSound, errorSound; // IDs for success and error sounds
    private TextView attemptsTextView; // TextView to display remaining attempts

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        initializeUI();

        // Load settings from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("GameSettings", MODE_PRIVATE);
        isUnlimitedMode = prefs.getBoolean("unlimitedMode", true);
        attemptsLeft = isUnlimitedMode ? -1 : prefs.getInt("attempts", 5);

        // Initialize sounds
        initializeSounds();

        // Generate numbers and set up the game
        generateNumbers();
        setupButtons();
        updateAttemptsDisplay();

        // Show the initial instruction dialog
        showInstructionDialog();
    }

    // Initialize UI elements (buttons, TextViews, stars)
    private void initializeUI() {
        numberButtons[0] = findViewById(R.id.button1);
        numberButtons[1] = findViewById(R.id.button2);
        numberButtons[2] = findViewById(R.id.button3);
        numberButtons[3] = findViewById(R.id.button4);
        numberButtons[4] = findViewById(R.id.button5);

        numberTextViews[0] = findViewById(R.id.textView1);
        numberTextViews[1] = findViewById(R.id.textView2);
        numberTextViews[2] = findViewById(R.id.textView3);
        numberTextViews[3] = findViewById(R.id.textView4);
        numberTextViews[4] = findViewById(R.id.textView5);

        starViews[0] = findViewById(R.id.star1);
        starViews[1] = findViewById(R.id.star2);
        starViews[2] = findViewById(R.id.star3);
        starViews[3] = findViewById(R.id.star4);
        starViews[4] = findViewById(R.id.star5);

        attemptsTextView = findViewById(R.id.attemptsTextView);
    }

    // Initialize sounds using SoundPool
    private void initializeSounds() {
        soundPool = new SoundPool.Builder().setMaxStreams(2).build();
        successSound = soundPool.load(this, R.raw.success, 1); // success.ogg in res/raw
        errorSound = soundPool.load(this, R.raw.alarm, 1); // error.ogg in res/raw
    }

    // Generate 5 random numbers in the range 10-99
    private void generateNumbers() {
        numbers.clear();
        Random random = new Random();
        while (numbers.size() < 5) {
            int num = random.nextInt(90) + 10; // Numbers from 10 to 99
            if (!numbers.contains(num)) {
                numbers.add(num);
            }
        }
        Collections.sort(numbers); // Sort to determine the order
    }

    // Set up buttons: set numbers and click listeners
    private void setupButtons() {
        ArrayList<Integer> shuffledNumbers = new ArrayList<>(numbers);
        Collections.shuffle(shuffledNumbers); // Shuffle for display
        for (int i = 0; i < 5; i++) {
            numberButtons[i].setText(String.valueOf(shuffledNumbers.get(i)));
            numberTextViews[i].setText("?");
            final int index = i;
            numberButtons[i].setOnClickListener(v -> handleButtonClick(index, shuffledNumbers.get(index)));
        }
    }

    // Handle button click
    private void handleButtonClick(int buttonIndex, int number) {
        numberTextViews[buttonIndex].setText(numberButtons[buttonIndex].getText());
        if (number == numbers.get(currentIndex)) {
            // Correct click
            handleCorrectClick(buttonIndex);
        } else {
            // Incorrect click
            handleIncorrectClick(buttonIndex);
        }
    }

    // Handle correct click
    private void handleCorrectClick(int buttonIndex) {
        // Scale animation for the button
        Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale_button);
        numberButtons[buttonIndex].startAnimation(scale);

        // Play success sound
        soundPool.play(successSound, 1, 1, 0, 0, 1);

        // Show the number in the TextView and disable the button
        numberButtons[buttonIndex].setEnabled(false);

        // Light up the progress star
        starViews[currentIndex].setImageResource(R.drawable.ok_star);

        // Show an encouraging message
        String[] successMessages = getResources().getStringArray(R.array.success_messages);
        Toast.makeText(this, successMessages[new Random().nextInt(successMessages.length)], Toast.LENGTH_SHORT).show();

        currentIndex++;
        if (currentIndex == 5) {
            // All numbers selected correctly
            showWinDialog();
        }
    }

    // Handle incorrect click
    private void handleIncorrectClick(int buttonIndex) {
        // Shake animation for the button
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_button);
        numberButtons[buttonIndex].startAnimation(shake);

        // Play error sound
        soundPool.play(errorSound, 1, 1, 0, 0, 1);

        // Show a guiding message
        String[] errorMessages = getResources().getStringArray(R.array.error_messages);
        Toast.makeText(this, errorMessages[new Random().nextInt(errorMessages.length)], Toast.LENGTH_SHORT).show();

        // Decrease attempts if in limited mode
        if (!isUnlimitedMode) {
            attemptsLeft--;
            updateAttemptsDisplay();
            if (attemptsLeft <= 0) {
                showOutOfAttemptsDialog();
            } else if (attemptsLeft <= 3) {
                // Hint: highlight the correct button
                for (int i = 0; i < 5; i++) {
                    if (numberButtons[i].getText().equals(String.valueOf(numbers.get(currentIndex)))) {
                        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse_button);
                        numberButtons[i].startAnimation(pulse);
                        break;
                    }
                }
            }
        }
    }

    // Update the display of remaining attempts
    private void updateAttemptsDisplay() {
        if (isUnlimitedMode) {
            attemptsTextView.setText(R.string.attempts_unlimited);
        } else {
            attemptsTextView.setText(getString(R.string.attempts_format, attemptsLeft));
        }
    }

    // Show the initial instruction dialog
    private void showInstructionDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.instruction_dialog_title)
                .setMessage(R.string.instruction_dialog_message)
                .setPositiveButton(R.string.dialog_button_start, (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }

    // Dialog on winning
    private void showWinDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.win_dialog_title)
                .setMessage(R.string.win_dialog_message)
                .setPositiveButton(R.string.dialog_button_new_round, (dialog, which) -> resetGame())
                .setNegativeButton(R.string.dialog_button_settings, (dialog, which) -> {
                    startActivity(new Intent(this, SettingsActivity.class));
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    // Dialog when out of attempts
    private void showOutOfAttemptsDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.out_of_attempts_dialog_title)
                .setMessage(R.string.out_of_attempts_dialog_message)
                .setPositiveButton(R.string.dialog_button_start_over, (dialog, which) -> resetGame())
                .setNegativeButton(R.string.dialog_button_settings, (dialog, which) -> {
                    startActivity(new Intent(this, SettingsActivity.class));
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    // Reset the game for a new round
    private void resetGame() {
        currentIndex = 0;
        SharedPreferences prefs = getSharedPreferences("GameSettings", MODE_PRIVATE);
        attemptsLeft = isUnlimitedMode ? -1 : prefs.getInt("attempts", 5);
        for (ImageView star : starViews) {
            star.setImageResource(R.drawable.no_star);
        }
        generateNumbers();
        setupButtons();
        updateAttemptsDisplay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release(); // Release SoundPool resources
    }
}

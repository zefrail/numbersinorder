# Class Description: SettingsActivity

## 1. General Information
*   **Class Name:** `SettingsActivity`
*   **Type:** `Activity`
*   **Purpose:** This screen allows the user to choose how they want to play the game: either with unlimited attempts or with a specific number of attempts.
*   **Interaction:** Receives the user from `Start` activity, saves their preferences, and passes them to `MainActivity` to start the actual game.

## 2. Variables (Class Fields)

| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `modeRadioGroup` | `RadioGroup` | A container for the selection buttons (Unlimited vs Limited). | `onCreate()` |
| `unlimitedModeRadio` | `RadioButton` | The button for "Unlimited" mode. | `onCreate()` |
| `limitedModeRadio` | `RadioButton` | The button for "Limited" mode. | `onCreate()` |
| `attemptsInput` | `EditText` | An input field where the user types the number of attempts. | `onCreate()` |
| `startButton` | `Button` | The button that starts the game. | `onCreate()` |
| `isUnlimited` | `boolean` | Stores `true` if unlimited, `false` otherwise. | `onCreate()` |
| `attempts` | `int` | Stores the number of attempts chosen by the user. | `onCreate()` |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `protected`
*   **Return value:** `void`
*   **Parameters:** `Bundle savedInstanceState`
*   **What does it do:** 
    1.  Links the Java variables to the XML UI elements.
    2.  Reads previously saved settings from `SharedPreferences`.
    3.  Sets the initial state of the buttons based on saved data.
    4.  Sets a listener on the `RadioGroup`: if "Limited" is selected, it enables the `attemptsInput` field.
    5.  Sets a listener on the `startButton` to validate input, save settings, and start the game.
*   **When called:** When the screen is opened.

## 4. Lifecycle
*   **`onCreate()`**: Used to set up the screen and load settings.

## 5. Interface Interaction (UI)
*   **`RadioGroup` / `RadioButton`**: Used for choosing one option out of two.
*   **`EditText`**: Used for text/number input.
*   **`Button`**: Used to trigger the transition logic.
*   **`Toast`**: Small pop-up messages used to warn the user if they typed an invalid number of attempts (e.g., 0 or negative).

## 6. Interaction with other components
*   **`SharedPreferences`**: This is a small "database" (a XML file) where we save the game settings so the app remembers them even if you close it.
*   **`Intent`**: Used to go to `MainActivity`.

## 7. General Logic
1. User chooses a mode.
2. If "Limited", user enters a number.
3. User clicks "Start".
4. The app checks if the number is valid.
5. If valid, the app saves the choice and opens the game.

## 8. Simplified Explanation
**Analogy:** Think of this screen like a "Game Settings" menu in a board game. Before you start, you decide: "Are we playing for fun without counting points (Unlimited)?" or "Do we only have 5 tries each (Limited)?". Once you decide and write it down (Save), you can start playing.

**Pro-tip:** The code uses `try-catch` when reading the number of attempts. This is very important! If a user leaves the field empty or types something that isn't a number, `Integer.parseInt` would crash the app. The `try-catch` prevents this.

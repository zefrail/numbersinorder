# Class Description: MainActivity

## 1. General Information
*   **Class Name:** `MainActivity`
*   **Type:** `Activity`
*   **Purpose:** This is the heart of the game. It generates random numbers, displays buttons, and checks if the user clicks them in the correct ascending order.
*   **Interaction:** Receives settings from `SettingsActivity` and manages the game flow.

## 2. Variables (Class Fields)

| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `numberButtons` | `Button[]` | An array (list) of 5 buttons that show the numbers. | Throughout the class |
| `numberTextViews` | `TextView[]` | Shows "?" initially and the number after clicking. | Throughout the class |
| `starViews` | `ImageView[]` | Shows progress stars (gray or yellow). | `resetGame()`, `handleCorrectClick()` |
| `numbers` | `ArrayList<Integer>` | Stores the 5 random numbers in sorted order. | `generateNumbers()`, `handleButtonClick()` |
| `currentIndex` | `int` | Tracks which number in the sequence we are currently looking for. | `handleCorrectClick()`, `resetGame()` |
| `attemptsLeft` | `int` | How many lives the player has left. | `handleIncorrectClick()`, `resetGame()` |
| `soundPool` | `SoundPool` | Tool to play short sound effects (win/lose). | `initializeSounds()`, `onDestroy()` |

## 3. Classroom Methods

### Method name: `generateNumbers`
*   **Type:** `private`
*   **What it does:** Uses a `Random` generator to pick 5 unique numbers between 10 and 99. It then sorts them so the app knows the correct order.

### Method name: `handleButtonClick`
*   **Parameters:** `int buttonIndex`, `int number`
*   **What it does:** Checks if the `number` on the button matches the `numbers.get(currentIndex)`. If it matches, calls `handleCorrectClick`. Otherwise, calls `handleIncorrectClick`.

### Method name: `handleIncorrectClick`
*   **What it does:** Plays an error sound, shows a shake animation on the button, and reduces the number of attempts. If attempts reach 0, it shows a "Game Over" dialog.

## 4. Lifecycle
*   **`onCreate()`**: Loads UI, sounds, and settings. Starts the game.
*   **`onDestroy()`**: Releases the `SoundPool` memory. This is important because audio hardware resources are limited!

## 5. Interface Interaction (UI)
*   **Arrays of Views:** Instead of managing 5 buttons separately, the code uses arrays (`Button[]`). This allows using `for` loops to set up all buttons at once, making the code cleaner.
*   **Animations:**
    *   `scale_button`: Pop effect on success.
    *   `shake_button`: Wobble effect on error.
    *   `pulse_button`: Hint effect (flashing) when the user is struggling.

## 6. Interaction with other components
*   **`AlertDialog`**: Used to show pop-up windows for "You Win", "Game Over", or "Instructions". This keeps the user focused on the game status.

## 7. General Logic
1. Five random numbers are generated and sorted.
2. The buttons are shuffled so they appear in a random order.
3. The user must find the smallest number, then the second smallest, etc.
4. Correct clicks light up stars. Incorrect clicks take away lives.

## 8. Simplified Explanation
**Analogy:** Imagine 5 locked boxes with numbers inside. You are told: "Open them in order from smallest to largest". Every time you pick the right box, you get a gold star. If you pick the wrong one, a buzzer sounds and you lose a "life". 

**Pro-tip:** The use of `Collections.shuffle()` is a smart way to make the game different every time. Even though the numbers are fixed, their position on the screen changes!

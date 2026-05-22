# 📱 Android Application Documentation "NumbersInOrder"

---

## 🧾 General Information
**Project Name:**
NumbersInOrder
**Author(s):**
Zeev Fraiman
**Date:**
May 2024
**Language:**
Java
**IDE:**
Android Studio
**Android Version (minSdk / targetSdk):**
28 / 36

---

## 🎯 Project Goal
• **Problem Solved:** Helps users (especially children) practice number comparison and sequencing skills in a gamified way.
• **Importance:** Develops logical thinking and attentiveness through interactive engagement.
• **Target Audience:** Primary school children and anyone wishing to train their reaction speed and number line knowledge.

---

## 📌 Requirements
**Functional Requirements**
• Generation of 5 random numbers in the range of 10 to 99.
• Choice of game mode: unlimited attempts or a limited number.
• Visual progress tracking using stars.
• Audio feedback for correct and incorrect actions.
• UI element animations.

**Non-Functional Requirements**
• **Performance:** Smooth animations and instant touch response.
• **Usability:** Simple, intuitive interface with large buttons.
• **Reliability:** Proper handling of invalid input (in settings) and stability during screen transitions.

---

## 🧠 General Architecture
• **Approach:**
– MVC (Model-View-Controller).
• **Why Chosen:** For a small gaming project, this approach allows for quick implementation where the Activity serves as the controller managing state (Model) and display (View).
• **Main Components:**
- `Start`: Splash Screen with animation.
- `SettingsActivity`: Game parameter configuration.
- `MainActivity`: Core game logic.
- `SharedPreferences`: Game settings storage.

---

## 🧩 UML Diagram
`[Start] –> [SettingsActivity] –> [MainActivity]`
`[MainActivity] <–> [SharedPreferences]`
`[MainActivity] –> [SoundPool / Animations]`

---

## Package Structure
The project uses a flat package structure: `zeev.fraiman.numbersinorder`.
- **Purpose:** At the current scale, it simplifies access to classes.
- **Scaling:** If expanded, packages like `ui`, `logic`, and `data` will be introduced.

---

## 🧩 Detailed Class Description

### 📌 Class: Start
**Role:** Application entry point.
**Responsibility:** Displaying a welcome animation and transitioning to settings.
**Key Methods:**
- `onCreate()`: Initializes TextView width and color animations.

### 📌 Class: SettingsActivity
**Role:** Configuration management.
**Responsibility:** Choosing the game mode and saving parameters to `SharedPreferences`.

### 📌 Class: MainActivity
**Role:** Game heart.
**Responsibility:** Handling clicks, verifying number order, managing sounds and animations.
**Key Methods:**
- `generateNumbers()`: Creates and sorts the number list.
- `handleButtonClick()`: Checks the correctness of the chosen number.
- `showWinDialog()`: Displays results upon winning.

---

## 🔄 App Workflow Diagram
1. User sees the loading screen (`Start`).
2. Proceeds to `SettingsActivity`, chooses a mode (e.g., 5 attempts).
3. In `MainActivity`, 5 buttons with random numbers appear.
4. User clicks numbers in ascending order.
5. On error: shake animation and attempt reduction.
6. On success: star lights up, button is locked.
7. After 5 correct clicks — win screen appears.

---

## 🎨 UI/UX Analysis
• **Design Choice:** Bright elements, large buttons, and the use of stars make the process clear and engaging.
• **Principles:**
– **Simplicity:** Minimal text.
– **Logic:** Numbers are hidden under "?" until clicked, mimicking a guessing game.
– **Accessibility:** Use of contrasting colors and animations.
• **Improvements:** Add a dark theme and the ability to choose the number range.

---

## ⚙️ Threading
• **Used:**
- `Handler` (in `Start` for delayed transition).
- Internal `SoundPool` threads for audio playback.
• **Why:** Standard Android tools for simple sync tasks and multimedia.
• **Prevention:**
– `handler.removeCallbacksAndMessages(null)` in `onDestroy` to prevent memory leaks.

---

## 💾 Data Handling
• **Storage:** `SharedPreferences`.
• **Why:** Optimal for simple settings (flags, numbers).
• **Durability:** Data persists between application sessions.

---

## 🔐 Security
• No sensitive data present. The app works locally and requires no special permissions.

---

## 🧪 Testing
• **Unit tests:** Checking number sorting logic.
• **UI tests:** Verifying screen transitions and button activations.

---

## 🐞 Error Handling
• Input validation for the number of attempts (handling `NumberFormatException`).
• Resource availability checks (sounds, animations).

---

## ⚡ Performance
• Optimization via `SoundPool` (pre-loading sounds).
• Lightweight XML animations.

---

## 🚀 Future Expansion
• Adding an online leaderboard.
• Increasing the count of numbers or adding difficulty levels.
• Multi-language UI support.

---

## 📊 Self-Assessment
| Criterion | Rating (1–10) |
| :--- | :--- |
| Architecture | 8 |
| Code | 9 |
| UI/UX | 9 |
| Reliability | 10 |
| **Overall Level** | **9** |

---

## 🏁 Conclusion
• **Best Part:** Feedback system (sounds + animations).
• **Challenge:** Tuning animation timings on the splash screen.
• **Skills:** Working with `ObjectAnimator`, `SoundPool`, and `SharedPreferences`.

---

## 📎 Appendices
• Screenshots: (to be added to the repository).
• Repository link: [NumbersInOrder](https://github.com/user/NumbersInOrder)

# AnimateCustomKeyBoard

# Animate CustomKeyBoard in Jetpack Compose

This project is a unique and engaging Android application that features a custom keyboard and an interactive 
animation effect. When a user types a letter on the custom keyboard, that letter "floats" from the keyboard 
to a designated box at the top of the screen, filling the box. This app showcases the power of Jetpack Compose 
for building dynamic and visually appealing user interfaces.


## Key Features

*   **Custom Keyboard:** A unique keyboard designed specifically for this app, different from the standard Android keyboard.
*   **Floating Letter Animation:** Letters smoothly animate from the keyboard to designated boxes.
*   **Dynamic Box Filling:** Letters fill boxes at the top of the screen, with the app managing which boxes are empty or full.
*   **Smooth Animations:** Uses Jetpack Compose's animation APIs for a fluid and engaging user experience.
*   **Reset Functionality:** A reset button clears all filled boxes and resets the app to its initial state.
*   **Backspace Functionality:** A backspace button to remove the last letter.
*   **Jetpack Compose:** Built entirely with Jetpack Compose for a modern and declarative UI.
*   **Learning:** The app can be used to learn how to use Jetpack Compose.

## Technologies Used

*   **Kotlin:** The primary programming language.
*   **Jetpack Compose:** For building the UI.
*   **`Animatable`:** For creating smooth animations.
*   **`LaunchedEffect`:** For triggering animations.
*   **`remember`, `mutableStateOf`:** For state management.
*   **`Row`, `Column`, `Box`:** For UI layout.
*   **`Offset`:** For managing positions.
*   **`onGloballyPositioned`:** For getting the position of the boxes.
*   **`UUID`:** For generating unique IDs.
*   **`Triple`:** For managing the letters.
*   **`Modifier`:** For modifying the UI elements.

## Getting Started

### Prerequisites

*   Android Studio (latest version recommended)
*   Android SDK (API level 26 or higher)

### Installation

1.  **Clone the repository:** 
    https://github.com/GeoSid/AnimateCustomKeyBoard.git
2.  **Open the project in Android Studio.**
3.  **Sync Project with Gradle Files:**
    *   Click "Sync Now" in the notification bar or go to `File > Sync Project with Gradle Files`.
4.  **Build and Run:**
    *   Click the "Run" button in Android Studio or use the command:
    *    bash ./gradlew run

## How to Use

1.  Launch the app on an Android device or emulator.
2.  Tap the keys on the custom keyboard.
3.  Observe the letters floating from the keyboard to the boxes at the top.
4.  Press the reset button to clear the boxes.
5.  Press the backspace button to remove the last letter.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please feel free to open an issue or submit a pull request.
## Video

Check the video [test_compose_gen_animate](https://github.com/GeoSId/AnimateCustomKeyBoard/blob/master/test_compose_gen_animate.webm)

[compose_test.webm](https://github.com/user-attachments/assets/a7e0e0d1-0802-4fd0-9dea-04acea12b834)


## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

package com.geosid.animatekeyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class CustomKeyTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Composable
    fun MockKeyboard(onKeyClick: (String, Offset) -> Unit, onBackspace: () -> Unit, onReset: () -> Unit) {
        Column {
//            Text(text = "A", modifier = Modifier.testTag("key_A").background(Color.Red).size(40.dp).performClick {
//                onKeyClick("A", Offset(0f, 0f))
//            })
//            Text(text = "B", modifier = Modifier.testTag("key_B").background(Color.Red).size(40.dp).performClick {
//                onKeyClick("B", Offset(0f, 0f))
//            })
//            Text(text = "C", modifier = Modifier.testTag("key_C").background(Color.Red).size(40.dp).performClick {
//                onKeyClick("C", Offset(0f, 0f))
//            })
//            Text(text = "Reset", modifier = Modifier.testTag("key_Reset").background(Color.Red).size(40.dp).performClick {
//                onReset()
//            })
//            Text(text = "Backspace", modifier = Modifier.testTag("key_Backspace").background(Color.Red).size(40.dp).performClick {
//                onBackspace()
//            })
        }
    }

    @Test
    fun singleLetterAnimation() = runTest {
        composeTestRule.setContent {
            CustomKey(onReset = {}, keyboard = { onKeyClick, onBackspace, onReset ->
                MockKeyboard(onKeyClick, onBackspace, onReset)
            })
        }

        composeTestRule.onNodeWithTag("key_A").performClick()
        composeTestRule.awaitIdle()
        composeTestRule.onNodeWithText("A").assertExists()
    }

    @Test
    fun multipleLettersSequentialAnimation() = runTest {
        composeTestRule.setContent {
            CustomKey(onReset = {}, keyboard = { onKeyClick, onBackspace, onReset ->
                MockKeyboard(onKeyClick, onBackspace, onReset)
            })
        }

        composeTestRule.onNodeWithTag("key_A").performClick()
        composeTestRule.awaitIdle()
        composeTestRule.onNodeWithText("A").assertExists()

        composeTestRule.onNodeWithTag("key_B").performClick()
        composeTestRule.awaitIdle()
        composeTestRule.onNodeWithText("B").assertExists()
    }

    @Test
    fun multipleLettersRapidAnimation() = runTest {
        composeTestRule.setContent {
            CustomKey(onReset = {}, keyboard = { onKeyClick, onBackspace, onReset ->
                MockKeyboard(onKeyClick, onBackspace, onReset)
            })
        }

        composeTestRule.onNodeWithTag("key_A").performClick()
        composeTestRule.onNodeWithTag("key_B").performClick()
        composeTestRule.onNodeWithTag("key_C").performClick()
        composeTestRule.awaitIdle()
        composeTestRule.onNodeWithText("A").assertExists()
        composeTestRule.onNodeWithText("B").assertExists()
        composeTestRule.onNodeWithText("C").assertExists()
    }

    @Test
    fun noAvailableBoxes() = runTest {
        composeTestRule.setContent {
            var filledBoxes by remember { mutableStateOf(mutableListOf<String?>().apply { repeat(5) { add("X") } }) }
            var floatingLetters by remember { mutableStateOf(listOf<Triple<String, Offset, String>>()) }
            CustomKey(onReset = {}, filledBoxes = filledBoxes, floatingLetters = floatingLetters, keyboard = { onKeyClick, onBackspace, onReset ->
                MockKeyboard(onKeyClick, onBackspace, onReset)
            })
        }

        composeTestRule.onNodeWithTag("key_A").performClick()
        composeTestRule.awaitIdle()
        composeTestRule.onNodeWithText("A").assertDoesNotExist()
    }

    @Test
    fun reset() = runTest {
        composeTestRule.setContent {
            var reset by remember { mutableStateOf(false) }
            CustomKeyboardScreen(reset)
        }

        composeTestRule.onNodeWithTag("key_A").performClick()
        composeTestRule.awaitIdle()
        composeTestRule.onNodeWithText("A").assertExists()
        composeTestRule.onNodeWithTag("key_Reset").performClick()
        composeTestRule.awaitIdle()
        composeTestRule.onNodeWithText("A").assertDoesNotExist()
    }

    @Test
    fun backspace() = runTest {
        composeTestRule.setContent {
            CustomKey(onReset = {}, keyboard = { onKeyClick, onBackspace, onReset ->
                MockKeyboard(onKeyClick, onBackspace, onReset)
            })
        }

        composeTestRule.onNodeWithTag("key_A").performClick()
        composeTestRule.awaitIdle()
        composeTestRule.onNodeWithText("A").assertExists()
        composeTestRule.onNodeWithTag("key_Backspace").performClick()
        composeTestRule.awaitIdle()
        composeTestRule.onNodeWithText("A").assertDoesNotExist()
    }
}

@Composable
fun CustomKey(
    onReset: () -> Unit,
    filledBoxes: MutableList<String?> = mutableListOf<String?>().apply { repeat(5) { add(null) } },
    floatingLetters: List<Triple<String, Offset, String>> = listOf(),
    keyboard: @Composable ((onKeyClick: (String, Offset) -> Unit, onBackspace: () -> Unit, onReset: () -> Unit) -> Unit)
) {
    var myFilledBoxes by remember { mutableStateOf(filledBoxes) }
    var myFloatingLetters by remember { mutableStateOf(floatingLetters) }
    keyboard(
        onKeyClick = { letter, position ->
            val id = java.util.UUID.randomUUID().toString()
            myFloatingLetters = myFloatingLetters + Triple(letter, position, id)
        },
        onBackspace = {
            if (myFilledBoxes.isNotEmpty()) {
                val lastFilledIndex = myFilledBoxes.indexOfLast { it != null }
                if (lastFilledIndex != -1) {
                    myFilledBoxes[lastFilledIndex] = null
                }
            }
        },
        onReset = {
            myFilledBoxes.forEachIndexed { index, _ ->
                myFilledBoxes[index] = null
            }
            onReset()
        }
    )
}
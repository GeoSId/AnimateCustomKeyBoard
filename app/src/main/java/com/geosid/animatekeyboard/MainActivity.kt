package com.geosid.animatekeyboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomKeyboardScreen(false)
        }
    }
}

@Composable
fun CustomKeyboardScreen(reset : Boolean) {
    val resetScreen = remember { mutableStateOf(reset) }
    if (resetScreen.value) {
        CustomKey(onReset = {
            resetScreen.value = !resetScreen.value
        })
    } else {
        CustomKey(onReset = {
            resetScreen.value = true
        })
    }
}

 @Composable
 fun CustomKey(onReset: () -> Unit) {
     val filledBoxes by remember { mutableStateOf(mutableListOf<String?>().apply { repeat(5) { add(null) } }) }
     val boxesPositions by remember { mutableStateOf(ArrayList<Offset>()) }
     var floatingLetters by remember { mutableStateOf(listOf<Triple<String, Offset, String>>()) }

     Row(
         modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
         horizontalArrangement = Arrangement.SpaceEvenly
     ) {
         filledBoxes.forEach { letter ->
             Column(modifier = Modifier
                 .onGloballyPositioned { coordinates ->
                     val pos = coordinates.positionInRoot()
                     boxesPositions.add(Offset(pos.x.plus(10), pos.y.plus(10)))
                 }
             ) {
                 Box(
                     modifier = Modifier.size(50.dp)
                         .background(MaterialTheme.colorScheme.onBackground),
                     contentAlignment = Alignment.Center
                 ) {
                     letter?.let {
                         Text(text = it, color = Color.White, fontSize = 24.sp)
                     }
                 }
             }
         }
     }

     Box(modifier = Modifier.fillMaxSize()) {
         Column(
             modifier = Modifier.fillMaxSize(),
             verticalArrangement = Arrangement.Bottom,
             horizontalAlignment = Alignment.CenterHorizontally
         ) {
             Keyboard(
                 onKeyClick = { letter, position ->
                     val id = UUID.randomUUID().toString()
                     floatingLetters = floatingLetters + Triple(letter, position, id)

                 }, onBackspace =
                 {

                 },
                 onReset = onReset
             )
         }

         floatingLetters.forEach { (letter, position, id) ->
             FloatingLetter(letter, position, boxesPositions, filledBoxes,
                 onAnimationEnd = {
                 // After each animation ends, remove the letter from the list of floating letters
                 floatingLetters = floatingLetters.filterNot { it.third == id }
             })
         }
     }
 }

@Composable
fun Keyboard(onKeyClick: (String, Offset) -> Unit,
             onBackspace: () -> Unit,
             onReset: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomKeyboard(
            onKeyClick = onKeyClick,
            onBackspace = onBackspace,
            onReset = onReset
        )
    }
}

@Composable
fun FloatingLetter(letter: String, startPosition: Offset, boxesPositions: List<Offset>, filledBoxes: MutableList<String?>, onAnimationEnd: () -> Unit) {
    var currentBoxIndex by remember { mutableStateOf(filledBoxes.indexOfFirst { it == null }) }
    if (currentBoxIndex == -1) return // No available box

    val targetPosition = boxesPositions[currentBoxIndex]
    val offsetX = remember { Animatable(startPosition.x) }
    val offsetY = remember { Animatable(startPosition.y) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(letter) {
        // Animate towards the target position
        coroutineScope.launch { offsetX.animateTo(targetPosition.x, animationSpec = tween(1000)) }
        coroutineScope.launch { offsetY.animateTo(targetPosition.y, animationSpec = tween(1000)) }
        // After the first animation finishes, check for available box and continue the animation
        delay(1000)
        filledBoxes[currentBoxIndex] = letter // Place the letter in the box
        // Update the index to the next available box
        currentBoxIndex = filledBoxes.indexOfFirst { it == null }
        if (currentBoxIndex != 0) {
            // If there's another empty box, animate the letter to the next position
            onAnimationEnd() // This allows for the removal of the previous floating letter, triggering the next one.
        }
    }

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .size(40.dp)
            .background(MaterialTheme.colorScheme.onBackground),
        contentAlignment = Alignment.Center
    ) {
        Text(text = letter, color = Color.White, fontSize = 24.sp)
    }
}

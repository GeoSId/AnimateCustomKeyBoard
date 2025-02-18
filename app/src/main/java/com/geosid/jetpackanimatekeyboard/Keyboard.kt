package com.geosid.jetpackanimatekeyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geosid.animatekeyBoard.R

@Composable
internal fun CustomKeyboard(
    modifier: Modifier = Modifier,
    onKeyClick: (String, Offset) -> Unit,
    onBackspace: () -> Unit,
    onReset: () -> Unit,
    keyboardKeys: KeyboardKeys = KeyboardKeys.English()
) {

    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            repeat(10) {
                val key = keyboardKeys.keys[it]
                KeyboardKey(key, onKeyClick, Modifier.weight(1f))
            }
        }
        Spacer(Modifier.size(4.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(start = 8.dp)
        ) {

            repeat(9) {
                val key = keyboardKeys.keys[10 + it]
                KeyboardKey(key, onKeyClick, Modifier.weight(1f))
            }
        }
        Spacer(Modifier.size(4.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier) {

            repeat(7) {
                val key = keyboardKeys.keys[19 + it]
                KeyboardKey(key, onKeyClick, Modifier.weight(1f))
            }

            KeyboardKey(
                text = "⌫",
                modifier = Modifier.width(40.dp),
                onClick = onBackspace
            )
        }
        Spacer(Modifier.size(6.dp))
        Box(
            modifier
                .align(CenterHorizontally)
                .height(40.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(MaterialTheme.colorScheme.primary)
                .clickable(onClick = onReset), Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.reset),
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,

                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
        }
    }
}

@Composable
private fun KeyboardKey(
    key: KeyboardKeys.Key?,
    onKeyClick: (String, Offset) -> Unit,
    modifier: Modifier = Modifier,
) {
    var keyPosition by remember { mutableStateOf<Offset?>(null) }

    KeyboardKey(modifier = modifier
        .onGloballyPositioned { coordinates ->
            val pos = coordinates.positionInRoot()
            keyPosition = Offset(pos.x, pos.y)
        },
        key?.button.toString().uppercase(),

    ) {
        keyPosition?.let { pos ->
            onKeyClick(key?.button.toString(), pos)
        }
    }
}

@Composable
private fun KeyboardKey(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Box(modifier
        .height(40.dp)
        .clip(RoundedCornerShape(2.dp))
        .background(MaterialTheme.colorScheme.onBackground)
        .clickable(onClick = {
            onClick()
        }), Alignment.Center) {
        Text(
            modifier = Modifier,
            text = text,
            color = Color.White,
            fontSize = 24.sp
        )
    }
}
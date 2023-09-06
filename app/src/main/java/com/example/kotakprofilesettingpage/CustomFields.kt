package com.example.kotakprofilesettingpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CustomTextField(
    title: String = "title",
    color: Color = Color.Black,
    font: Int = R.font.frutiger,
    textSize: Dp = 14.dp,
    textAlign: TextAlign = TextAlign.Center,
    modifier: Modifier = Modifier,

    ) {
    Text(text = title,
        style = TextStyle(color = color,
            fontFamily = FontFamily(fonts = listOf(Font(font))),
            fontSize = dpToSp(dp = textSize),
            textAlign = textAlign),modifier = modifier)
}


@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaneTextField(text: MutableState<String>, label: String) {
    TextField(
        value = text.value,
        onValueChange = {
            text.value = it
        },
        modifier = Modifier.background(color = Color.White).fillMaxWidth(),
        placeholder = {
            Text(text = label)
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            disabledTextColor = Color.Black,
            focusedIndicatorColor = Color.Black,
            cursorColor = Color.Black
        )
    )
}
package com.munyroth.majorrecommendation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun ScoreInputField(label: String, maxScore: Int, onScoreChanged: (Float) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue()) }

    OutlinedTextField(
        value = text,
        onValueChange = { newValue ->
            // Check for maximum length
            if (newValue.text.isEmpty()) {
                text = newValue
            } else if (newValue.text.toFloatOrNull() != null) {
                if (newValue.text.toFloat() in 0f..maxScore.toFloat()) {
                    text = newValue

                    val leadingZero = newValue.text.length > 1 && newValue.text.startsWith("0")
                    val leadingDot = newValue.text.startsWith("0.")

                    text = if (!leadingZero || leadingDot) {
                        newValue
                    } else {
                        TextFieldValue(
                            text = newValue.text.substring(1),
                            selection = newValue.selection
                        )
                    }
                }
            }

            onScoreChanged(text.text.toFloatOrNull()?.div(maxScore.toFloat()) ?: 0F)
        },
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        trailingIcon = {
            Text(
                text = "/$maxScore",
                color = Color.DarkGray,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    )
}
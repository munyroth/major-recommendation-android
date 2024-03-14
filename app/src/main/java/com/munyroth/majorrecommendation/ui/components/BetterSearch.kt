package com.munyroth.majorrecommendation.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BetterSearch(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
) {

    var query by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    var isClear by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        TextField(
            modifier = modifier
                .animateContentSize()
                .align(Alignment.CenterEnd)
                .then(
                    if (isActive) {
                        modifier.fillMaxWidth()
                    } else {
                        modifier.width(54.dp)
                    }
                ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            value = query,
            onValueChange = {
                query = it
                if (it.isNotEmpty()) {
                    isClear = false
                    onSearch(query)
                } else {
                    isClear = true
                }
            },
            enabled = isActive,
            shape = CircleShape,
            singleLine = true,
            placeholder = {
                if (isActive) {
                    Text(
                        text = "Search",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            },
            leadingIcon = {
                IconButton(
                    modifier = Modifier.padding(4.dp),
                    onClick = {
                        isActive = !isActive
                    }) {

                    if (!isActive) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null
                        )
                    } else {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(
                        modifier = Modifier.padding(4.dp),
                        onClick = { query = "" }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}
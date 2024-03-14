package com.munyroth.majorrecommendation.ui.components

import android.app.Activity
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BetterScaffold(
    title: String,
    isSearch: Boolean = false,
    searchContent: @Composable () -> Unit = { },
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            style = MaterialTheme.typography.titleLarge
                                .copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            modifier = Modifier
                                .animateContentSize()
                                .align(Alignment.CenterStart)
                                .then(
                                    if (isSearch) {
                                        Modifier.width(0.dp)
                                    } else {
                                        Modifier
                                    }
                                ),
                            text = title,
                        )

                        searchContent()
                    }
                },
                navigationIcon = {
                    if (!isSearch) {
                        IconButton(
                            onClick = {
                                (context as Activity).onBackPressed()
                            }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        },
        content = content
    )
}
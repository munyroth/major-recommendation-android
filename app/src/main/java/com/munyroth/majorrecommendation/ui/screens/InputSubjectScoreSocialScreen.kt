package com.munyroth.majorrecommendation.ui.screens

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.munyroth.majorrecommendation.request.RecommendationRequest
import com.munyroth.majorrecommendation.ui.components.ScoreInputField
import com.munyroth.majorrecommendation.ui.theme.AppTheme

@Composable
fun InputSubjectScoreSocialScreen() {
    val scores = remember { mutableStateOf(RecommendationRequest()) }
    val isClicked = remember { mutableStateOf(false) }

    InputSubjectScoreScreen(
        scores = scores,
        isClicked = isClicked,
        isAnyFieldEmpty = {
            scores.value.khmer == 0F || scores.value.maths == 0F || scores.value.history == 0F || scores.value.geography == 0F || scores.value.morality == 0F || scores.value.english == 0F
        }
    ) {
        ScoreInputField(
            label = "KHMER",
            maxScore = 125,
            onErrorAction = isClicked.value && scores.value.khmer == 0F
        ) { score ->
            isClicked.value = false
            scores.value.khmer = score
        }
        ScoreInputField(
            label = "MATHS",
            maxScore = 75,
            onErrorAction = isClicked.value && scores.value.maths == 0F
        ) { score ->
            isClicked.value = false
            scores.value.maths = score
        }
        ScoreInputField(
            label = "HISTORY",
            maxScore = 75,
            onErrorAction = isClicked.value && scores.value.history == 0F
        ) { score ->
            isClicked.value = false
            scores.value.history = score
        }
        ScoreInputField(
            label = "GEOGRAPHY",
            maxScore = 75,
            onErrorAction = isClicked.value && scores.value.geography == 0F
        ) { score ->
            isClicked.value = false
            scores.value.geography = score
        }
        ScoreInputField(
            label = "MORALITY",
            maxScore = 75,
            onErrorAction = isClicked.value && scores.value.morality == 0F
        ) { score ->
            isClicked.value = false
            scores.value.morality = score
        }
        ScoreInputField(
            label = "ENGLISH",
            maxScore = 50,
            onErrorAction = isClicked.value && scores.value.english == 0F
        ) { score ->
            isClicked.value = false
            scores.value.english = score
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun InputSubjectScoreSocialPreview() {
    AppTheme {
        InputSubjectScoreSocialScreen()
    }
}
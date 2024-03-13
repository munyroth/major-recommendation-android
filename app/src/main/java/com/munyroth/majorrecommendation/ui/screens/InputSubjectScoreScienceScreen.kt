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
fun InputSubjectScoreScienceScreen() {
    val scores = remember { mutableStateOf(RecommendationRequest()) }
    val isClicked = remember { mutableStateOf(false) }

    InputSubjectScoreScreen(
        scores = scores,
        isClicked = isClicked,
        isAnyFieldEmpty = {
            scores.value.khmer == 0F || scores.value.maths == 0F || scores.value.physics == 0F || scores.value.chemistry == 0F || scores.value.biology == 0F || scores.value.english == 0F
        }
    ) {
        ScoreInputField(
            label = "MATHS",
            maxScore = 125,
            onErrorAction = isClicked.value && scores.value.maths == 0F
        ) { score ->
            isClicked.value = false
            scores.value.maths = score
        }
        ScoreInputField(
            label = "KHMER",
            maxScore = 75,
            onErrorAction = isClicked.value && scores.value.khmer == 0F
        ) { score ->
            isClicked.value = false
            scores.value.khmer = score
        }
        ScoreInputField(
            label = "PHYSICS",
            maxScore = 75,
            onErrorAction = isClicked.value && scores.value.physics == 0F
        ) { score ->
            isClicked.value = false
            scores.value.physics = score
        }
        ScoreInputField(
            label = "CHEMISTRY",
            maxScore = 75,
            onErrorAction = isClicked.value && scores.value.chemistry == 0F
        ) { score ->
            isClicked.value = false
            scores.value.chemistry = score
        }
        ScoreInputField(
            label = "BIOLOGY",
            maxScore = 75,
            onErrorAction = isClicked.value && scores.value.biology == 0F
        ) { score ->
            isClicked.value = false
            scores.value.biology = score
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
fun InputSubjectScoreSciencePreview() {
    AppTheme {
        InputSubjectScoreScienceScreen()
    }
}
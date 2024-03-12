package com.munyroth.majorrecommendation.ui.screens

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.request.RecommendationRequest
import com.munyroth.majorrecommendation.ui.activity.ResultRecommendationActivity
import com.munyroth.majorrecommendation.ui.components.BetterScaffold
import com.munyroth.majorrecommendation.ui.components.ScoreInputField
import com.munyroth.majorrecommendation.ui.theme.AppTheme
import com.munyroth.majorrecommendation.viewmodel.RecommendationViewModel

@Composable
fun InputSubjectScoreSocialScreen(
    recommendationViewModel: RecommendationViewModel = viewModel()
) {
    val context = LocalContext.current
    val scores = remember { mutableStateOf(RecommendationRequest()) }

    val result = recommendationViewModel.recommendation.value
    when (result.status) {
        Status.SUCCESS -> {
            val gson = Gson()
            val json = gson.toJson(result.data?.data)

            val intent = Intent(context, ResultRecommendationActivity::class.java)
            intent.putExtra("data", json)
            context.startActivity(intent)
        }

        Status.ERROR -> {
            // Handle error state
        }

        Status.LOADING -> {
            // Handle loading state
        }

        else -> {
            // Handle other states
        }
    }

    BetterScaffold(title = stringResource(id = R.string.app_name)) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.label_fill_score),
                modifier = Modifier.padding(16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                ScoreInputField(label = "KHMER", maxScore = 125) { score ->
                    scores.value.khmer = score
                }
                ScoreInputField(label = "MATHS", maxScore = 75) { score ->
                    scores.value.maths = score
                }
                ScoreInputField(label = "HISTORY", maxScore = 75) { score ->
                    scores.value.history = score
                }
                ScoreInputField(label = "GEOGRAPHY", maxScore = 75) { score ->
                    scores.value.geography = score
                }
                ScoreInputField(label = "MORALITY", maxScore = 75) { score ->
                    scores.value.morality = score
                }
                ScoreInputField(label = "ENGLISH", maxScore = 50) { score ->
                    scores.value.english = score
                }
            }

            Button(
                onClick = {
                    recommendationViewModel.recommendation(scores.value)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.End)
            ) {
                Text(text = stringResource(id = R.string.next))
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_next_white),
                    contentDescription = null
                )
            }
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
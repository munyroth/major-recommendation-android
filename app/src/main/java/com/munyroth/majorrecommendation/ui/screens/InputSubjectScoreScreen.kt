package com.munyroth.majorrecommendation.ui.screens

import android.content.Intent
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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.request.RecommendationRequest
import com.munyroth.majorrecommendation.activity.ResultRecommendationActivity
import com.munyroth.majorrecommendation.ui.components.BetterScaffold
import com.munyroth.majorrecommendation.viewmodel.RecommendationViewModel

@Composable
fun InputSubjectScoreScreen(
    recommendationViewModel: RecommendationViewModel = viewModel(),
    scores: MutableState<RecommendationRequest>,
    isClicked: MutableState<Boolean>,
    isAnyFieldEmpty: () -> Boolean,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val result = recommendationViewModel.recommendation.value
    when (result.status) {
        Status.SUCCESS -> {
            val gson = Gson()
            val json = gson.toJson(result.data?.data)

            val intent = Intent(context, ResultRecommendationActivity::class.java)
            intent.putExtra("data", json)
            context.startActivity(intent)
        }

        else -> {
            // Handle other states
        }
    }

    BetterScaffold(title = stringResource(id = R.string.title_major_recommendation)) { innerPadding ->
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
                content()
            }
            Button(
                onClick = {
                    if (isAnyFieldEmpty()) {
                        isClicked.value = true
                        return@Button
                    }
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

package com.munyroth.majorrecommendation.ui.screens

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.model.Recommendation
import com.munyroth.majorrecommendation.ui.activity.MainActivity
import com.munyroth.majorrecommendation.ui.screens.viewholder.ResultRecommendationViewHolder
import com.munyroth.majorrecommendation.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultRecommendationScreen(
    data: List<Recommendation>
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = MaterialTheme.typography.titleLarge
                            .copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = stringResource(id = R.string.app_name),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        (context as? Activity)?.finish()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.label_result_recommendation),
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(data) { item ->
                    ResultRecommendationViewHolder(
                        title = item.major,
                        probability = item.probability + "%",
                        imageUrl = "https://i.pinimg.com/originals/e3/c1/8a/e3c18aa096cc01b16b7a3bae1da6aee0.jpg"
                    )
                }
            }

            Button(
                onClick = {
                    val activityMain = Intent(context, MainActivity::class.java)
                    activityMain.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(activityMain)
                    (context as? Activity)?.finish()
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.finish))
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ResultRecommendationScreenPreview() {
    AppTheme {
        ResultRecommendationScreen(
            data = List(3) { Recommendation("Computer Science", "80") }
        )
    }
}

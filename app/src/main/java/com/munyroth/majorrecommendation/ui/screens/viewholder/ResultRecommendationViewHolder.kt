package com.munyroth.majorrecommendation.ui.screens.viewholder

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.ui.theme.AppTheme

@Composable
fun ResultRecommendationViewHolder(
    title: String,
    probability: String,
    imageUrl: String
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )

        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = probability,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleSmall
                    .copy(color = MaterialTheme.colorScheme.onSecondary)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_angle_small_right),
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
        )
    }
}

@Preview
@Composable
fun ResultRecommendationViewHolderPreview() {
    AppTheme {
        ResultRecommendationViewHolder(
            title = "Computer Science",
            probability = "80%",
            imageUrl = "https://i.pinimg.com/originals/e3/c1/8a/e3c18aa096cc01b16b7a3bae1da6aee0.jpg"
        )
    }
}

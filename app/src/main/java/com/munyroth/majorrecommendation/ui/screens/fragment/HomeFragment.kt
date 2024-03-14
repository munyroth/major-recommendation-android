package com.munyroth.majorrecommendation.ui.screens.fragment

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.activity.MajorActivity
import com.munyroth.majorrecommendation.activity.SelectClassStudyActivity
import com.munyroth.majorrecommendation.activity.UniversityActivity
import com.munyroth.majorrecommendation.ui.theme.AppTheme

@Composable
fun Home() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ElevatedCard(
            onClick = {
                val intent = Intent(context, SelectClassStudyActivity::class.java)
                context.startActivity(intent)
            },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
            ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_search_major),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
                Text(
                    text = stringResource(id = R.string.title_major_recommendation),
                    style = MaterialTheme.typography.titleMedium
                        .copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.ic_angle_small_right),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            ElevatedCard(
                onClick = {
                    val intent = Intent(context, MajorActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_major),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.title_majors),
                        style = MaterialTheme.typography.titleMedium
                            .copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            ElevatedCard(
                onClick = {
                    val intent = Intent(context, UniversityActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_university),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.title_universities),
                        style = MaterialTheme.typography.titleMedium
                            .copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomePreview() {
    AppTheme {
        Home()
    }
}

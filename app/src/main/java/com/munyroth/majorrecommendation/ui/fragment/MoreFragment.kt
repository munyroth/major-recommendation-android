package com.munyroth.majorrecommendation.ui.fragment

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.ui.theme.AppTheme

@Composable
fun More(showBottomSheet: Boolean, setShowBottomSheet: (Boolean) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .clickable { setShowBottomSheet(true) }
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painterResource(R.drawable.ic_language),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
                    )
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        style = MaterialTheme.typography.titleSmall,
                        text = stringResource(id = R.string.title_change_language),
                    )
                }
                Image(
                    painterResource(R.drawable.ic_angle_small_right),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
                )
            }
        }
    }
}

@Preview
@Composable
fun MorePreview() {
    AppTheme {
        More(false) {

        }
    }
}
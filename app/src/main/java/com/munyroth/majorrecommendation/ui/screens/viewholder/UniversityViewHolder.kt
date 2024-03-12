package com.munyroth.majorrecommendation.ui.screens.viewholder

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.model.Faculty
import com.munyroth.majorrecommendation.model.University
import com.munyroth.majorrecommendation.ui.activity.UniversityDetailActivity
import com.munyroth.majorrecommendation.ui.theme.AppTheme
import java.util.Locale

@Composable
fun UniversityViewHolder(
    university: University,
) {
    val context = LocalContext.current
    val languageCode = Locale.getDefault().language

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val intent = Intent(context, UniversityDetailActivity::class.java)
                intent.putExtra("university_id", university.id)
                intent.putExtra(
                    "university_name",
                    if (languageCode == "km") university.nameKm else university.nameEn
                )
                intent.putExtra("university_logo", university.logo)
                context.startActivity(intent)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = university.logo,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .padding(start = 16.dp),
        )

        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (languageCode == "km") university.nameKm else university.nameEn,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Image(
            modifier = Modifier
                .size(40.dp)
                .padding(end = 16.dp),
            painter = painterResource(id = R.drawable.ic_angle_small_right),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
        )
    }
}

@Preview
@Composable
fun UniversityViewHolderPreview() {
    AppTheme {
        UniversityViewHolder(
            university = University(
                id = 1,
                universityType = "Public",
                nameKm = "សាកលវិទ្យាល័យប្រជាជនជាតិនិមិត្តសញ្ញាប័ត្រ",
                nameEn = "Royal University of Phnom Penh",
                aboutKm = "សាកលវិទ្យាល័យប្រជាជនជាតិនិមិត្តសញ្ញាប័ត្រ គឺជាសាកលវិទ្យាល័យជាតិដែលបង្កើតឡើងនៅឆ្នាំ១៩៦៤",
                aboutEn = "The Royal University of Phnom Penh is the national university of Cambodia, which was established in 1964",
                logo = "https://www.rupp.edu.kh/images/logo.png",
                website = "https://www.rupp.edu.kh/",
                email = "",
                phone = "",
                createdAt = "2021-10-10",
                updatedAt = "2021-10-10",
                faculties = listOf(
                    Faculty(
                        id = 1,
                        nameKm = "វិទ្យាសាស្រ្តវិទ្យាល័យ",
                        nameEn = "Faculty of Science",
                        createdAt = "2021-10-10",
                        updatedAt = "2021-10-10",
                        departments = emptyList()
                    )
                )
            )
        )
    }
}

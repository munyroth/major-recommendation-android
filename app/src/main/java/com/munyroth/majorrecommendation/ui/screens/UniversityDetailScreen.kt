package com.munyroth.majorrecommendation.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.Department
import com.munyroth.majorrecommendation.model.Faculty
import com.munyroth.majorrecommendation.model.ResData
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.model.University
import com.munyroth.majorrecommendation.ui.components.BetterScaffold
import com.munyroth.majorrecommendation.ui.components.DisplayError
import com.munyroth.majorrecommendation.ui.components.DisplayLoading
import com.munyroth.majorrecommendation.ui.components.ExpandableText
import com.munyroth.majorrecommendation.ui.theme.AppTheme
import com.munyroth.majorrecommendation.viewmodel.UniversityDetailViewModel
import java.util.Locale

@Composable
fun UniversityDetailScreen(
    id: Int,
    name: String,
    universityDetailViewModel: UniversityDetailViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        universityDetailViewModel.loadUniversity(id = id)
    }

    BetterScaffold(title = name) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            UniversityDetailContent(
                university = universityDetailViewModel.university.value
            )
        }
    }
}

@Composable
fun UniversityDetailContent(university: ApiData<ResData<University>>) {
    when (university.status) {
        Status.LOADING -> {
            DisplayLoading()
        }

        Status.SUCCESS -> {
            // Display the university details
            UniversityDetailScreen(university = university.data?.data!!)
        }

        Status.ERROR -> {
            DisplayError(message = "An error occurred while loading the university")
        }

        else -> {
            // Handle other states
        }
    }
}

@Composable
fun UniversityDetailScreen(
    university: University
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = university.logo,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )

            Text(
                text = if (Locale.getDefault().language == "km") university.nameKm else university.nameEn,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Text(
                text = university.universityType,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            ExpandableText(
                text = university.aboutEn,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = R.string.label_contact),
            style = MaterialTheme.typography.titleMedium
        )

        ContactItem(
            icon = R.drawable.ic_phone_call,
            text = university.phone?.let { "0$it" } ?: "",
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0${university.phone}"))
                context.startActivity(intent)
            }
        )
        ContactItem(
            icon = R.drawable.ic_envelope,
            text = university.email ?: "",
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${university.email}"))
                context.startActivity(intent)
            }
        )
        ContactItem(
            icon = R.drawable.ic_site_browser,
            text = university.website ?: "",
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https:${university.website}"))
                context.startActivity(intent)
            }
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = R.string.label_faculty),
            style = MaterialTheme.typography.titleMedium
        )

        university.faculties?.forEach { faculty ->
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = if (Locale.getDefault().language == "km") faculty.nameKm else faculty.nameEn,
                style = MaterialTheme.typography.labelLarge
            )
            faculty.departments.forEach { department ->
                Text(
                    color = MaterialTheme.colorScheme.onSecondary,
                    text = if (Locale.getDefault().language == "km") department.nameKm else department.nameEn,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
fun ContactItem(
    icon: Int,
    text: String,
    onClick: () -> Unit = { }
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            modifier = Modifier.clickable { onClick() },
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = Color.Blue
        )
    }
}

@Preview
@Composable
fun UniversityDetailScreenPreview() {
    AppTheme {
        UniversityDetailScreen(
            id = 1,
            name = "University of Cambodia"
        )
    }
}

@Preview
@Composable
fun UniversityDetailContentPreview() {
    AppTheme {
        UniversityDetailScreen(
            university = University(
                id = 1,
                nameKm = "សាកលវិទ្យាល័យភូមិន្ទភ្នំពេញ",
                nameEn = "Royal University of Phnom Penh",
                universityType = "Private",
                aboutKm = "សាកលវិទ្យាល័យភូមិន្ទភ្នំពេញ (RUPP) ត្រូវបានបង្កើតឡើងក្នុងឆ្នាំ 1960 បានឆ្លងកាត់ការផ្លាស់ប្តូរជាបន្តបន្ទាប់ ដើម្បីក្លាយជាសាកលវិទ្យាល័យជាតិឈានមុខគេនៅកម្ពុជា។ ការផ្លាស់ប្តូរផ្សេងទៀតនៅតែកើតឡើង។ ជាឧទាហរណ៍ ក្នុងរយៈពេលប្រាំឆ្នាំចុងក្រោយនេះ RUPP មានការរីកចម្រើនគួរឱ្យកត់សម្គាល់ក្នុងវិស័យជាច្រើន រួមទាំងរចនាសម្ព័ន្ធអង្គការ ការគ្រប់គ្រងស្ថាប័ន ការកសាងសមត្ថភាព ការអភិវឌ្ឍន៍ហេដ្ឋារចនាសម្ព័ន្ធ ការស្រាវជ្រាវ ការបង្រៀន និងការសិក្សា ការអភិវឌ្ឍន៍កម្មវិធីសិក្សា និងការធានាគុណភាព។ សមិទ្ធិផលទាំងនេះបានលើកទឹកចិត្តដល់គណៈគ្រប់គ្រង និងមហាវិទ្យាល័យរបស់ RUPP ឱ្យខិតខំអនុវត្តកំណែទម្រង់ស្ថាប័ន និងសម្រេចបាននូវគោលដៅអភិវឌ្ឍន៍។",
                aboutEn = "The Royal University of Phnom Penh (RUPP), founded in 1960, has undergone a series of transformations to become the leading national university in Cambodia. Other transformations are still happening. In the last five years, for example, RUPP has made considerable progresses in many areas including organizational structure, institutional governance, capacity building, infrastructure development, research, teaching and learning, curriculum development, and quality assurance. These achievements have encouraged RUPP management and faculty to strive harder to implement the institutional reform and achieve development goals.",
                logo = "https://munyroth.me/logo.png",
                phone = "12345678",
                email = "2820.muny.roth@rupp.edu.kh",
                website = "https://rupp.edu.kh",
                createdAt = "",
                updatedAt = "",
                faculties = List(10) { fId ->
                    Faculty(
                        id = fId,
                        nameKm = "មហាវិទ្យាល័យវិទ្យាសាស្ត្រ",
                        nameEn = "Faculty of Engineering",
                        createdAt = "",
                        updatedAt = "",
                        departments = List(5) { dId ->
                            Department(
                                id = dId,
                                nameKm = "ដេប៉ាតឺម៉ង់វិទ្យា",
                                nameEn = "Computer Science",
                                createdAt = "",
                                updatedAt = ""
                            )
                        }
                    )
                }
            )
        )
    }
}
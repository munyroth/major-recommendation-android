package com.munyroth.majorrecommendation.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.ResData
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.model.University
import com.munyroth.majorrecommendation.ui.components.DisplayError
import com.munyroth.majorrecommendation.ui.components.DisplayLoading
import com.munyroth.majorrecommendation.ui.screens.viewholder.UniversityViewHolder
import com.munyroth.majorrecommendation.ui.theme.AppTheme
import com.munyroth.majorrecommendation.viewmodel.UniversityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversityScreen(
    universityViewModel: UniversityViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        universityViewModel.loadUniversities()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = MaterialTheme.typography.titleLarge
                            .copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = stringResource(id = R.string.title_universities),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        (context as? Activity)?.finish()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            UniversityContent(
                universities = universityViewModel.universities.value
            )
        }
    }
}

@Composable
fun UniversityContent(universities: ApiData<ResData<List<University>>>) {
    when (universities.status) {
        Status.LOADING -> {
            DisplayLoading()
        }

        Status.SUCCESS -> {
            // Display the list of universities
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(universities.data?.data ?: emptyList()) { item ->
                    UniversityViewHolder(university = item)
                }
            }
        }

        Status.ERROR -> {
            DisplayError(message = "An error occurred while loading universities")
        }

        else -> {
            // Handle other states
        }
    }
}

@Preview
@Composable
fun UniversityScreenPreview() {
    AppTheme {
        UniversityScreen()
    }
}

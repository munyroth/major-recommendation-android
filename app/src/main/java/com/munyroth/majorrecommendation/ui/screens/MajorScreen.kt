package com.munyroth.majorrecommendation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.Major
import com.munyroth.majorrecommendation.model.ResData
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.ui.components.BetterScaffold
import com.munyroth.majorrecommendation.ui.components.BetterSearchBar
import com.munyroth.majorrecommendation.ui.components.DisplayError
import com.munyroth.majorrecommendation.ui.components.DisplayLoading
import com.munyroth.majorrecommendation.ui.screens.viewholder.MajorViewHolder
import com.munyroth.majorrecommendation.ui.theme.AppTheme
import com.munyroth.majorrecommendation.viewmodel.MajorViewModel

@Composable
fun MajorScreen(
    majorViewModel: MajorViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        majorViewModel.loadMajors()
    }

    BetterScaffold(title = stringResource(id = R.string.title_majors)) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var majors by remember { mutableStateOf(emptyList<Major>()) }

            BetterSearchBar(
                onSearch = { query ->
                    majors = majorViewModel.searchMajors(query)
                },
                result = {
                    majors.forEach { major ->
                        MajorViewHolder(major = major)
                    }
                }
            )

            MajorContent(majors = majorViewModel.majors.value)
        }
    }
}

@Composable
fun MajorContent(majors: ApiData<ResData<List<Major>>>) {
    when (majors.status) {
        Status.LOADING -> {
            DisplayLoading()
        }

        Status.SUCCESS -> {
            // Display the list of universities
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(majors.data?.data ?: emptyList()) { item ->
                    MajorViewHolder(major = item)
                }
            }
        }

        Status.ERROR -> {
            DisplayError(message = "An error occurred while loading the majors")
        }

        else -> {
            // Handle other states
        }
    }
}

@Preview
@Composable
fun MajorScreenPreview() {
    AppTheme {
        MajorScreen()
    }
}

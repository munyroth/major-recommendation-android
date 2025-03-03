package com.munyroth.majorrecommendation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.ResData
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.model.University
import com.munyroth.majorrecommendation.ui.components.BetterScaffold
import com.munyroth.majorrecommendation.ui.components.BetterSearch
import com.munyroth.majorrecommendation.ui.components.DisplayError
import com.munyroth.majorrecommendation.ui.components.ShimmerAnimation
import com.munyroth.majorrecommendation.ui.screens.viewholder.UniversityViewHolder
import com.munyroth.majorrecommendation.ui.theme.AppTheme
import com.munyroth.majorrecommendation.viewmodel.UniversityViewModel

@Composable
fun UniversityScreen(
    universityViewModel: UniversityViewModel = viewModel()
) {
    var isSearchActive by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        universityViewModel.loadUniversities()
    }

    BetterScaffold(
        title = stringResource(id = R.string.title_universities),
        isSearch = isSearchActive,
        searchContent = {
            BetterSearch(
                isActive = isSearchActive,
                onActive = { isSearchActive = it },
                onSearch = { query ->
                    universityViewModel.loadUniversities(query)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
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
            val maxShimmerCount = 12

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(maxShimmerCount) {
                    ShimmerAnimation()
                }
            }
        }

        Status.SUCCESS -> {
            val universityList = universities.data?.data ?: emptyList()
            if (universityList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.no_data_message)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(universityList) { item ->
                        UniversityViewHolder(university = item)
                    }
                }
            }
        }

        Status.ERROR -> {
            DisplayError(message = stringResource(id = R.string.error_message))
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

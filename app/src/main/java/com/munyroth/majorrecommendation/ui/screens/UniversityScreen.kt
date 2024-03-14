package com.munyroth.majorrecommendation.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.model.ApiData
import com.munyroth.majorrecommendation.model.ResData
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.model.University
import com.munyroth.majorrecommendation.ui.components.BetterScaffold
import com.munyroth.majorrecommendation.ui.components.DisplayError
import com.munyroth.majorrecommendation.ui.components.DisplayLoading
import com.munyroth.majorrecommendation.ui.screens.viewholder.UniversityViewHolder
import com.munyroth.majorrecommendation.ui.theme.AppTheme
import com.munyroth.majorrecommendation.viewmodel.UniversityViewModel

@Composable
fun UniversityScreen(
    universityViewModel: UniversityViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        universityViewModel.loadUniversities()
    }
    val isSearch = remember {
        mutableStateOf(false)
    }

    BetterScaffold(
        title = stringResource(id = R.string.title_universities),
        isSearch = isSearch.value,
        searchContent = {
            var textState by remember { mutableStateOf("") }
            val maxLength = 110

            val focusRequester = remember { FocusRequester() }
            val focusManager = LocalFocusManager.current

            Box(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    modifier = Modifier
                        .animateContentSize()
                        .focusRequester(focusRequester)
                        .align(Alignment.CenterEnd)
                        .then(
                            if (isSearch.value) {
                                Modifier.fillMaxWidth()
                            } else {
                                Modifier.width(54.dp)
                            }
                        ),
                    value = textState,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    onValueChange = {
                        if (it.length <= maxLength) textState = it
                    },
                    shape = CircleShape,
                    singleLine = true,
                    placeholder = {
                        if (isSearch.value) {
                            Text(
                                text = "Search",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    },
                    leadingIcon = {
                        IconButton(
                            modifier = Modifier.padding(4.dp),
                            onClick = {
                                if (!isSearch.value) focusRequester.requestFocus()
                                else {
                                    focusManager.clearFocus()
                                    textState = ""
                                }

                                isSearch.value = !isSearch.value
                            }) {

                            if (!isSearch.value) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = null
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    trailingIcon = {
                        if (textState.isNotEmpty()) {
                            IconButton(
                                modifier = Modifier.padding(4.dp),
                                onClick = { textState = "" }) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                )
            }
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

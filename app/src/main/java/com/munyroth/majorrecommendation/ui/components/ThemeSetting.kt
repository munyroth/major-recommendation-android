package com.munyroth.majorrecommendation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.model.enums.AppThemeEnum
import com.munyroth.majorrecommendation.ui.theme.AppTheme
import com.munyroth.majorrecommendation.utility.AppPreference
import com.munyroth.majorrecommendation.viewmodel.MainEvent
import com.munyroth.majorrecommendation.viewmodel.MainViewModel

@Composable
fun ThemeSettings(
    mainViewModel: MainViewModel = viewModel(),
) {
    val context = LocalContext.current
    val theme = AppPreference.get(context).getTheme()

    val radioOptions = mapOf(
        AppThemeEnum.SYSTEM.name to stringResource(id = R.string.system),
        AppThemeEnum.LIGHT.name to stringResource(id = R.string.light),
        AppThemeEnum.DARK.name to stringResource(id = R.string.dark)
    )

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(theme.name) }
    mainViewModel.onEvent(MainEvent.ThemeChange(AppThemeEnum.valueOf(selectedOption)))

    Column {
        GroupRadioContent(
            radioOptions = radioOptions,
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected
        )

        Button(
            onClick = {
                AppPreference.get(context).setTheme(AppThemeEnum.valueOf(selectedOption))
                mainViewModel.onEvent(MainEvent.ThemeChange(AppThemeEnum.valueOf(selectedOption)))
                mainViewModel.showBottomSheet = false
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ThemeSettingsPreview() {
    AppTheme {
        ThemeSettings()
    }
}


package com.munyroth.majorrecommendation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.model.enums.AppLanguageEnum
import com.munyroth.majorrecommendation.ui.theme.AppTheme
import com.munyroth.majorrecommendation.viewmodel.MainEvent
import com.munyroth.majorrecommendation.viewmodel.MainViewModel
import java.util.Locale

@Composable
fun LanguageSettings(
    mainViewModel: MainViewModel = viewModel(),
) {
    val languageCode = Locale.getDefault().language

    val radioOptions = mapOf(
        AppLanguageEnum.KM.languageCode to stringResource(id = R.string.khmer),
        AppLanguageEnum.EN.languageCode to stringResource(id = R.string.english)
    )

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(languageCode) }

    Column(Modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .selectable(
                        selected = (text.key == selectedOption),
                        onClick = { onOptionSelected(text.key) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text.key == selectedOption),
                    onClick = null
                )
                Text(
                    text = text.value,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        Button(
            onClick = {
                mainViewModel.showBottomSheet = false
                if (selectedOption != languageCode) {
                    mainViewModel.showLanguageDialog = true
                    mainViewModel.onEvent(MainEvent.LanguageChange(selectedOption))
                }
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
fun LanguageSettingsPreview() {
    AppTheme {
        LanguageSettings()
    }
}
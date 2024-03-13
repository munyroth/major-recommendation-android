package com.munyroth.majorrecommendation.ui.components

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.model.enums.AppThemeEnum
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
        AppThemeEnum.SYSTEM to stringResource(id = R.string.system),
        AppThemeEnum.LIGHT to stringResource(id = R.string.light),
        AppThemeEnum.DARK to stringResource(id = R.string.dark)
    )

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(theme) }

    Column(Modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .selectable(
                        selected = (text.key == selectedOption),
                        onClick = {
                            onOptionSelected(text.key)
                            mainViewModel.onEvent(MainEvent.ThemeChange(text.key))
                        },
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
                AppPreference.get(context).setTheme(selectedOption)
                mainViewModel.onEvent(MainEvent.ThemeChange(selectedOption))
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

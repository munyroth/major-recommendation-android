package com.munyroth.majorrecommendation.ui.fragment

import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.LocaleList
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.ui.activity.StartActivity
import com.munyroth.majorrecommendation.ui.components.BetterModalBottomSheet
import com.munyroth.majorrecommendation.ui.components.LanguageSettings
import com.munyroth.majorrecommendation.ui.components.ThemeSettings
import com.munyroth.majorrecommendation.ui.components.WarningDialog
import com.munyroth.majorrecommendation.ui.theme.AppTheme
import com.munyroth.majorrecommendation.utility.AppPreference
import com.munyroth.majorrecommendation.viewmodel.MainEvent
import com.munyroth.majorrecommendation.viewmodel.MainViewModel
import java.util.Locale

fun changeLocales(context: Context, localeString: String) {
    AppPreference.get(context).setLanguage(localeString)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java)
            .applicationLocales = LocaleList.forLanguageTags(localeString)
    } else {
        val intent = Intent(context, StartActivity::class.java)
        context.startActivity(intent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun More(
    mainViewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current
    val languageCode = Locale.getDefault().language

    val sheetState = rememberModalBottomSheetState()
//    val scope = rememberCoroutineScope()

    var bottomSheetContent by remember { mutableStateOf<@Composable () -> Unit>({}) }

    if (mainViewModel.showLanguageDialog) {
        WarningDialog(
            title = stringResource(id = R.string.change_language),
            message = stringResource(id = R.string.change_language_message),
            onConfirm = {
                if (mainViewModel.stateApp?.language != languageCode)
                    mainViewModel.stateApp?.language?.let { changeLocales(context, it) }
                mainViewModel.showLanguageDialog = false
            },
            onDismiss = {
                mainViewModel.showLanguageDialog = false
            }
        )
    }

    if (!mainViewModel.showBottomSheet) {
        mainViewModel.onEvent(
            MainEvent.ThemeChange(
                AppPreference.get(LocalContext.current).getTheme()
            )
        )
    }

    Column {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                ItemContent(
                    text = stringResource(id = R.string.title_change_language),
                    leadingImage = painterResource(R.drawable.ic_language)
                ) {
                    mainViewModel.showBottomSheet = true
                    bottomSheetContent = {
                        LanguageSettings(
                            mainViewModel
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }

            item {
                ItemContent(
                    text = stringResource(id = R.string.title_change_theme),
                    leadingImage = painterResource(R.drawable.ic_moon)
                ) {
                    mainViewModel.showBottomSheet = true
                    bottomSheetContent = {
                        ThemeSettings(
                            mainViewModel
                        )
                    }
                }
            }
        }
    }

    BetterModalBottomSheet(
        showSheet = mainViewModel.showBottomSheet,
        onDismissRequest = { mainViewModel.showBottomSheet = false },
        sheetState = sheetState
    ) {
        bottomSheetContent()
    }
}

@Composable
fun ItemContent(
    text: String,
    leadingImage: Painter,
    onClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .height(54.dp)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = leadingImage,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.titleMedium,
                text = text,
            )
        }
        Image(
            painter = painterResource(R.drawable.ic_angle_small_right),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
        )
    }
}

@Preview
@Composable
fun MorePreview() {
    AppTheme {
        More()
    }
}
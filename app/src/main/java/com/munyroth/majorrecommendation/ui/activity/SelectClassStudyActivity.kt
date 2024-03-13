package com.munyroth.majorrecommendation.ui.activity

import androidx.activity.compose.setContent
import com.munyroth.majorrecommendation.ui.screens.SelectClassStudyScreen
import com.munyroth.majorrecommendation.ui.theme.AppTheme

class SelectClassStudyActivity : BaseActivity() {
    override fun init() {
        setContent {
            AppTheme {
                SelectClassStudyScreen()
            }
        }
    }
}
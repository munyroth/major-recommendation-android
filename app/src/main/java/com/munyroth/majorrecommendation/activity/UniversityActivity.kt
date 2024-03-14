package com.munyroth.majorrecommendation.activity

import androidx.activity.compose.setContent
import com.munyroth.majorrecommendation.ui.screens.UniversityScreen
import com.munyroth.majorrecommendation.ui.theme.AppTheme

class UniversityActivity : BaseActivity() {
    override fun init() {
        setContent {
            AppTheme {
                UniversityScreen()
            }
        }
    }
}
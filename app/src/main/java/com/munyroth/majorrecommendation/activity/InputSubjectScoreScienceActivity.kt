package com.munyroth.majorrecommendation.activity

import androidx.activity.compose.setContent
import com.munyroth.majorrecommendation.ui.screens.InputSubjectScoreScienceScreen
import com.munyroth.majorrecommendation.ui.theme.AppTheme

class InputSubjectScoreScienceActivity : BaseActivity() {
    override fun init() {
        setContent {
            AppTheme {
                InputSubjectScoreScienceScreen()
            }
        }
    }
}
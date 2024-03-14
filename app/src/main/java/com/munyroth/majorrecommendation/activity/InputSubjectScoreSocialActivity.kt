package com.munyroth.majorrecommendation.activity

import androidx.activity.compose.setContent
import com.munyroth.majorrecommendation.ui.screens.InputSubjectScoreSocialScreen
import com.munyroth.majorrecommendation.ui.theme.AppTheme

class InputSubjectScoreSocialActivity : BaseActivity() {
    override fun init() {
        setContent {
            AppTheme {
                InputSubjectScoreSocialScreen()
            }
        }
    }
}
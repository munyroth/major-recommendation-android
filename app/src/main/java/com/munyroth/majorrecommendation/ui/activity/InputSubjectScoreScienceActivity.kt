package com.munyroth.majorrecommendation.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import com.munyroth.majorrecommendation.ui.screens.InputSubjectScoreScienceScreen
import com.munyroth.majorrecommendation.ui.theme.AppTheme

class InputSubjectScoreScienceActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                InputSubjectScoreScienceScreen()
            }
        }
    }
}
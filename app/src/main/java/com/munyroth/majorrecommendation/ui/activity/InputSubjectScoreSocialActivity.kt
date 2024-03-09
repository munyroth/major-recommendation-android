package com.munyroth.majorrecommendation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.munyroth.majorrecommendation.ui.screens.InputSubjectScoreSocialScreen
import com.munyroth.majorrecommendation.ui.theme.AppTheme

class InputSubjectScoreSocialActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                InputSubjectScoreSocialScreen()
            }
        }
    }
}
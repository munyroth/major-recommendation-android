package com.munyroth.majorrecommendation.activity

import androidx.activity.compose.setContent
import com.munyroth.majorrecommendation.ui.screens.MajorScreen
import com.munyroth.majorrecommendation.ui.theme.AppTheme

class MajorActivity : BaseActivity() {
    override fun init() {
        setContent{
            AppTheme {
                MajorScreen()
            }
        }
    }
}
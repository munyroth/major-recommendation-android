package com.munyroth.majorrecommendation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.munyroth.majorrecommendation.ui.screens.UniversityDetailScreen
import com.munyroth.majorrecommendation.ui.theme.AppTheme

class UniversityDetailActivity : ComponentActivity() {
    companion object {
        const val EXTRA_UNIVERSITY_ID = "university_id"
        const val EXTRA_UNIVERSITY_NAME = "university_name"
        const val EXTRA_UNIVERSITY_LOGO = "university_logo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                UniversityDetailScreen(
                    id = intent.getIntExtra(EXTRA_UNIVERSITY_ID, 0),
                    name = intent.getStringExtra(EXTRA_UNIVERSITY_NAME) ?: ""
                )
            }
        }
    }
}

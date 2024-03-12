package com.munyroth.majorrecommendation.ui.activity

import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import com.munyroth.majorrecommendation.utility.AppPreference
import java.util.Locale

open class BaseActivity : ComponentActivity() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(updateBaseContextLocale(newBase))
    }

    private fun updateBaseContextLocale(context: Context): Context {
        val language = AppPreference.get(context).getLanguage() ?: "km"
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
            configuration.setLocale(locale)
        }

        return context.createConfigurationContext(configuration)
    }
}
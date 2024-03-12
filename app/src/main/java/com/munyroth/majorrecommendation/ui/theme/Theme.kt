package com.munyroth.majorrecommendation.ui.theme

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.munyroth.majorrecommendation.model.enums.AppThemeEnum
import com.munyroth.majorrecommendation.utility.AppPreference

private val DarkColorScheme = darkColorScheme(
    primary = DarkGreen,
    onPrimary = Color.White,
    primaryContainer = Color.Black,
    onPrimaryContainer = Color.White,

    secondary = DarkGreen,
    onSecondary = LightGray,
    secondaryContainer = DarkGreen,
    onSecondaryContainer = Color.White,

    tertiary = DarkGray,
    onTertiary = Color.White,

    background = Color.Black,
    onBackground = Color.White,

    surface = DarkGray,
    onSurface = Color.White,
    surfaceVariant = DarkGreen,
    onSurfaceVariant = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = DarkGreen,
    onPrimary = Color.White,
    primaryContainer = PaleGreen,
    onPrimaryContainer = Color.Black,

    secondary = DarkGreen,
    onSecondary = Black99,
    secondaryContainer = DarkGreen,
    onSecondaryContainer = Color.White,

    tertiary = Color.White,
    onTertiary = Color.Black,

    background = Color.White,
    onBackground = Color.Black,

    surface = PaleGreen,
    onSurface = DarkGreen,
    surfaceVariant = DarkGreen,
    onSurfaceVariant = Black99,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = run {
        val context = LocalContext.current
        val isDark = when (AppPreference.get(context).getTheme()) {
            AppThemeEnum.SYSTEM -> isSystemInDarkTheme()
            AppThemeEnum.LIGHT -> false
            AppThemeEnum.DARK -> true
        }

        isDark
    },
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MainAppTheme(
    appTheme: AppThemeEnum,
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    Log.d("AppTheme", "appTheme: $appTheme")
    var isDark = darkTheme

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> when (appTheme) {
            AppThemeEnum.SYSTEM -> {
                Log.d("AppTheme", "darkTheme: $darkTheme")
                if (darkTheme) {
                    isDark = true
                    DarkColorScheme
                } else {
                    isDark = false
                    LightColorScheme
                }
            }
            AppThemeEnum.LIGHT -> {
                isDark = false
                LightColorScheme
            }
            AppThemeEnum.DARK -> {
                isDark = true
                DarkColorScheme
            }
        }
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            window.navigationBarColor = colorScheme.surfaceColorAtElevation(2.dp).toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDark
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
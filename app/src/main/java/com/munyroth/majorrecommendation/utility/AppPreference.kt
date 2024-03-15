package com.munyroth.majorrecommendation.utility

import android.content.Context
import android.content.SharedPreferences
import com.munyroth.majorrecommendation.model.enums.AppThemeEnum
import java.util.Locale
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class AppPreference private constructor(context: Context) {

    private var pref: SharedPreferences
    private var encryptedPref: SharedPreferences

    init {
        // Create an instance of SharedPreferences
        pref = context.getSharedPreferences("app_preference", Context.MODE_PRIVATE)

        // Create an instance of MasterKey
        val masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        // Create an instance of EncryptedSharedPreferences
        encryptedPref = EncryptedSharedPreferences.create(
            "app_preference_encrypted",
            masterKeys,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    // Token
    fun setToken(token: String) {
        encryptedPref.edit().putString(KEY_TOKEN, token).apply()
    }
    fun getToken(): String? {
        return encryptedPref.getString(KEY_TOKEN, null)
    }
    fun removeToken() {
        encryptedPref.edit().remove(KEY_TOKEN).apply()
    }

    // FCM Token
    fun setFcmToken(token: String) {
        encryptedPref.edit().putString(KEY_FCM_TOKEN, token).apply()
    }
    fun getFcmToken(): String? {
        return encryptedPref.getString(KEY_FCM_TOKEN, null)
    }

    // Language
    fun setLanguage(language: String) {
        pref.edit().putString(KEY_LANGUAGE, language).apply()
    }

    fun getLanguage(): String {
        return pref.getString(KEY_LANGUAGE, null) ?: Locale.getDefault().language
    }

    // Theme
    fun setTheme(theme: AppThemeEnum) {
        pref.edit().putString(KEY_THEME, theme.name).apply()
    }

    fun getTheme(): AppThemeEnum {
        return AppThemeEnum.valueOf(
            pref.getString(KEY_THEME, AppThemeEnum.SYSTEM.name)!!
        )
    }

    companion object {

        private const val KEY_TOKEN = "token"
        private const val KEY_FCM_TOKEN = "fcm_token"
        private const val KEY_LANGUAGE = "language"
        private const val KEY_THEME = "theme"

        private var instance: AppPreference? = null

        fun get(context: Context): AppPreference {
            if (instance == null) {
                instance = AppPreference(context)
            }

            return instance!!
        }
    }
}
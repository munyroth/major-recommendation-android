package com.munyroth.majorrecommendation.ui.activity

import android.content.Intent
import com.munyroth.majorrecommendation.databinding.ActivityChangeLanguageBinding
import com.munyroth.majorrecommendation.utility.AppPreference
import java.util.Locale

class ChangeLanguageActivity : BaseActivity<ActivityChangeLanguageBinding>(ActivityChangeLanguageBinding::inflate){
    override fun initActions() {
        val language = Locale.getDefault().language
        if (language == "en") {
            binding.rbEnglish.isChecked = true
        } else {
            binding.rbKhmer.isChecked = true
        }
    }

    override fun setupListeners() {
        binding.btnSave.setOnClickListener {
            val language = if (binding.rbEnglish.isChecked) "en" else "km"
            changeLanguage(language)
        }
    }

    override fun setupObservers() {

    }

    private fun changeLanguage(languageCode: String) {
        AppPreference.get(this).setLanguage(languageCode)

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
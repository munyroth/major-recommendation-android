package com.munyroth.majorrecommendation.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.munyroth.majorrecommendation.utility.AppPreference
import java.util.Locale

abstract class BaseActivity<T : ViewBinding>(
    private val bindingFunction: (LayoutInflater) -> T
) : AppCompatActivity() {

    private var _binding: T? = null
    val binding: T
        get() = _binding!!

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(updateBaseContextLocale(newBase))
    }

    private fun updateBaseContextLocale(context: Context): Context {
        val language = AppPreference.get(context).getLanguage() ?: "en"
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)

        return context.createConfigurationContext(configuration)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = bindingFunction(layoutInflater)
        setContentView(binding.root)

        initActions()
        setupListeners()
        setupObservers()
    }

    override fun onSupportNavigateUp(): Boolean {
        // Handle the back button click
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    abstract fun initActions()
    abstract fun setupListeners()
    abstract fun setupObservers()
}

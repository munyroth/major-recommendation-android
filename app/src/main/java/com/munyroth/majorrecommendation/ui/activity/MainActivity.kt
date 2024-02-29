package com.munyroth.majorrecommendation.ui.activity

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun initActions() {
        setupNavigation()
    }

    override fun setupListeners() {

    }

    override fun setupObservers() {

    }

    private fun setupNavigation() {
        val bottomNavView: BottomNavigationView = binding.bottomNavView

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment

        val navController = navHostFragment.navController

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top-level destinations.
        appBarConfiguration = AppBarConfiguration(navController.graph)

//        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)

        supportActionBar?.apply {
            // Set logo for action bar
            setDisplayShowHomeEnabled(true)
            val logoDrawable = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_app) // Get the drawable
            val resizedDrawable = resizeDrawable(logoDrawable!!) // Resize the drawable
            setLogo(resizedDrawable) // Set the resized drawable as the logo
            setDisplayUseLogoEnabled(true)
        }
    }

    // Function to resize a drawable
    private fun resizeDrawable(drawable: Drawable): Drawable {
        val bitmap = Bitmap.createScaledBitmap(
            (drawable as BitmapDrawable).bitmap,
            64,
            64,
            true
        )
        return BitmapDrawable(resources, bitmap)
    }
}
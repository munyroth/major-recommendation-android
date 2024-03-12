package com.munyroth.majorrecommendation.ui.activity

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.munyroth.majorrecommendation.ui.screens.MainScreen
import com.munyroth.majorrecommendation.ui.theme.MainAppTheme
import com.munyroth.majorrecommendation.viewmodel.MainViewModel

class MainActivity : BaseActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("MainActivityTest", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("MainActivityTest", "Token: $token")

            // Send the token to the server
            mainViewModel.sendFcmToken(token)
        })

        // Ask for notification permission
        askNotificationPermission()

        setContent {
            MainAppTheme(
                appTheme = mainViewModel.stateApp.theme
            ) {
                MainScreen(mainViewModel)
            }
        }
    }

    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // Inform the user that the app will not show notifications.
            Toast.makeText(this, "Notifications will not be enabled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // Display an educational UI explaining the benefits of granting the permission
                AlertDialog.Builder(this)
                    .setTitle("Enable Notifications")
                    .setMessage("Allow notifications to receive important updates and alerts.")
                    .setPositiveButton("OK") { dialog, _ ->
                        // User clicked OK, directly request the permission
                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        dialog.dismiss()
                    }
                    .setNegativeButton("No thanks") { dialog, _ ->
                        // User clicked No thanks, inform them that notifications will not be enabled
                        Toast.makeText(
                            this,
                            "Notifications will not be enabled",
                            Toast.LENGTH_SHORT
                        ).show()
                        dialog.dismiss()
                    }
                    .show()
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

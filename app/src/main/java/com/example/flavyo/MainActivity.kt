package com.example.flavyo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.example.flavyo.data.UserPreferences
import com.example.flavyo.databinding.ActivityMainBinding
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val userPreferences = UserPreferences(this)
        
        setContent {
            SplashScreenContainer(onTimeout = {
                if (userPreferences.isLoggedIn) {
                    // Navigate to Home if already logged in
                    startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                } else {
                    // Navigate to Onboarding if not logged in
                    startActivity(Intent(this@MainActivity, OnboardingActivity::class.java))
                }
                finish()
            })
        }
    }
}

@Composable
fun SplashScreenContainer(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1000) // 1-second splash delay
        onTimeout()
    }

    AndroidViewBinding(ActivityMainBinding::inflate) {
        // XML layout remains the same
    }
}

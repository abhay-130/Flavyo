package com.example.flavyo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.flavyo.screens.LoginScreen
import com.example.flavyo.ui.theme.FlavyoTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Retrieve the role from the Intent
        val role = intent.getStringExtra("ROLE") ?: "USER"
        val isAdmin = role == "ADMIN"

        setContent {
            FlavyoTheme {
                LoginScreen(
                    isAdmin = isAdmin,
                    onGoToSignup = {
                        startActivity(Intent(this, SignupActivity::class.java))
                    }
                )
            }
        }
    }
}

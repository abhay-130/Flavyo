package com.example.flavyo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.flavyo.screens.SignupScreen
import com.example.flavyo.ui.theme.FlavyoTheme

class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlavyoTheme {
                SignupScreen(
                    onGoToLogin = {
                        // Just close signup to go back to LoginActivity
                        finish()
                    }
                )
            }
        }
    }
}

package com.example.flavyo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.flavyo.screens.RoleSelectionScreen
import com.example.flavyo.ui.theme.FlavyoTheme

class RoleSelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlavyoTheme {
                RoleSelectionScreen(
                    onAdminClick = {
                        // Navigate to Admin Login/Panel (To be implemented)
                        startActivity(Intent(this, LoginActivity::class.java).apply {
                            putExtra("ROLE", "ADMIN")
                        })
                    },
                    onUserClick = {
                        startActivity(Intent(this, LoginActivity::class.java).apply {
                            putExtra("ROLE", "USER")
                        })
                    }
                )
            }
        }
    }
}

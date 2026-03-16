package com.example.flavyo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.example.flavyo.databinding.ActivityOnboardingBinding

class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnboardingScreen()
        }
    }
}

@Composable
fun OnboardingScreen() {
    val context = LocalContext.current
    AndroidViewBinding(ActivityOnboardingBinding::inflate) {
        btnNext.setOnClickListener {
            // Navigate to RoleSelectionActivity instead of LoginActivity
            val intent = Intent(context, RoleSelectionActivity::class.java)
            context.startActivity(intent)

            // Finish this activity
            (context as? ComponentActivity)?.finish()
        }
    }
}
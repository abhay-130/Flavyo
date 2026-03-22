package com.example.flavyo.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.AdminActivity
import com.example.flavyo.HomeActivity
import com.example.flavyo.R
import com.example.flavyo.data.AuthRequest
import com.example.flavyo.data.RetrofitClient
import com.example.flavyo.data.UserPreferences
import com.example.flavyo.ui.theme.CoveredByYourGrace
import com.example.flavyo.ui.theme.Poppins
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    isAdmin: Boolean, // New Parameter to distinguish role
    onGoToSignup: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userPreferences = remember { UserPreferences(context) }

    var emailPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Image(
            painter = painterResource(id = R.drawable.flavyo_logo_red),
            contentDescription = "Logo",
            modifier = Modifier.size(180.dp)
        )
        Text(
            text = "Flavours that melt you!",
            color = Color(0xFFC1272D),
            fontSize = 28.sp,
            letterSpacing = 2.sp,
            fontFamily = CoveredByYourGrace,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))

        // Dynamic Title based on Role
        Text(
            text = if (isAdmin) "Admin Login" else "Login To Your Account",
            color = Color.Gray,
            fontSize = 20.sp,
            fontFamily = Poppins
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = emailPhone,
            onValueChange = { emailPhone = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Email or Phone Number", fontFamily = Poppins) },
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Password", fontFamily = Poppins) },
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(40.dp))

        if (isLoading) {
            CircularProgressIndicator(color = Color(0xFFC1272D))
        } else {
            Button(
                onClick = {
                    if (emailPhone.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    isLoading = true
                    scope.launch {
                        try {
                            // We pass empty strings for name/address because they aren't needed for login
                            val request = AuthRequest(
                                name = "",
                                emailOrPhone = emailPhone,
                                address = "",
                                password = password
                            )

                            // DYNAMIC API CALL: Choose based on isAdmin parameter
                            val response = if (isAdmin) {
                                RetrofitClient.authApi.adminLogin(creds = request)
                            } else {
                                RetrofitClient.authApi.login(creds = request)
                            }

                            if (response.isSuccessful) {
                                val authResponse = response.body()

                                if (authResponse?.status == "success") {
                                    // Save Shared Preferences
                                    userPreferences.isLoggedIn = true
                                    userPreferences.userRole = if (isAdmin) "admin" else "user"
                                    userPreferences.userName = authResponse.user?.name ?: "User"
                                    userPreferences.userEmail = emailPhone
                                    userPreferences.userAddress = authResponse.user?.address ?: ""

                                    // DYNAMIC NAVIGATION: Go to AdminActivity or HomeActivity
                                    val destination = if (isAdmin) AdminActivity::class.java else HomeActivity::class.java
                                    context.startActivity(Intent(context, destination))

                                    (context as? android.app.Activity)?.finish()
                                } else {
                                    Toast.makeText(context, authResponse?.message ?: "Login failed", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "Server Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Connection Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                        } finally {
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC1272D)),
                shape = RoundedCornerShape(16.dp) // Updated to match TextField shape
            ) {
                Text("Login", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins)
            }
        }

        // Only show "Don't have account" for regular users
        if (!isAdmin) {
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onGoToSignup) {
                Text(
                    text = "Don't Have Account?",
                    color = Color(0xFFC1272D),
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins
                )
            }
        }
    }
}
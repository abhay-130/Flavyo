package com.example.flavyo.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.flavyo.R
import com.example.flavyo.data.AuthRequest
import com.example.flavyo.data.RetrofitClient
import com.example.flavyo.ui.theme.CoveredByYourGrace
import com.example.flavyo.ui.theme.Poppins
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(onGoToLogin: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var emailPhone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
            text = "Flavour that melt you!",
            color = Color(0xFFC1272D),
            fontSize = 28.sp,
            letterSpacing = 2.sp,
            fontFamily = CoveredByYourGrace,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Create Your Account", color = Color.Gray, fontSize = 20.sp, fontFamily = Poppins)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Name") }, shape = RoundedCornerShape(16.dp), singleLine = true)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = emailPhone, onValueChange = { emailPhone = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Email or Phone Number") }, shape = RoundedCornerShape(16.dp), singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = address, onValueChange = { address = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Address") }, shape = RoundedCornerShape(16.dp), singleLine = true)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = password, onValueChange = { password = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Password") }, shape = RoundedCornerShape(16.dp), singleLine = true, visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))

        Spacer(modifier = Modifier.height(40.dp))

        if (isLoading) {
            CircularProgressIndicator(color = Color(0xFFC1272D))
        } else {
            Button(
                onClick = {
                    if (name.isEmpty() || emailPhone.isEmpty() || address.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    isLoading = true
                    scope.launch {
                        try {
                            val request = AuthRequest(
                                name = name,
                                emailOrPhone = emailPhone,
                                address = address,
                                password = password
                            )
                            val response = RetrofitClient.authApi.signUp(user = request)

                            if (response.isSuccessful && response.body()?.status == "success") {
                                Toast.makeText(context, "Account created successfully", Toast.LENGTH_SHORT).show()
                                onGoToLogin()
                            } else {
                                val errorMsg = response.body()?.message ?: "Signup failed"
                                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Log.e("SignupError", e.stackTraceToString())
                            Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                        } finally {
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC1272D)),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text("Create Account", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = onGoToLogin) {
            Text(text = "Already Have An Account?", color = Color(0xFFC1272D), fontWeight = FontWeight.Bold, fontFamily = Poppins)
        }
    }
}

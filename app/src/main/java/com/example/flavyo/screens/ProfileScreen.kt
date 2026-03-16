package com.example.flavyo.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.data.AuthRequest
import com.example.flavyo.data.RetrofitClient
import com.example.flavyo.data.UserPreferences
import com.example.flavyo.ui.theme.Poppins
import kotlinx.coroutines.launch

@Composable
fun ProfileContent(onLogout: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userPreferences = remember { UserPreferences(context) }
    
    var isEditing by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    
    // States for the fields
    var name by remember { mutableStateOf(userPreferences.userName ?: "") }
    var address by remember { mutableStateOf(userPreferences.userAddress ?: "") }
    val emailPhone = userPreferences.userEmail ?: ""

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My Profile",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        if (isEditing) {
            EditableProfileField("Name", name) { name = it }
            EditableProfileField("Address", address) { address = it }
            ProfileField("Email/Phone (Not Editable)", emailPhone)
        } else {
            ProfileField("Name", name)
            ProfileField("Address", address)
            ProfileField("Email/Phone", emailPhone)
        }

        Spacer(modifier = Modifier.weight(1f))

        if (isLoading) {
            CircularProgressIndicator(color = Color(0xFFC1272D))
        } else {
            Button(
                onClick = {
                    if (isEditing) {
                        isLoading = true
                        scope.launch {
                            try {
                                // Create a request object that matches your Script's 'data.emailOrPhone'
                                val updateRequest = AuthRequest(
                                    name = name,
                                    emailOrPhone = emailPhone, // Column B identifier
                                    address = address,
                                    password = "" // Password not needed for update, but required by model
                                )

                                // Use POST 'update' action
                                val response = RetrofitClient.authApi.updateProfile(user = updateRequest)

                                // Google Script returns an OBJECT {status: "success"}, not a list []
                                val authResponse = response.body()

                                if (response.isSuccessful && authResponse?.status == "success") {
                                    // Update local storage
                                    userPreferences.userName = name
                                    userPreferences.userAddress = address

                                    Toast.makeText(context, "Profile Updated in Excel!", Toast.LENGTH_SHORT).show()
                                    isEditing = false
                                } else {
                                    val errorMsg = authResponse?.message ?: "Update failed"
                                    Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "Network Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                            } finally {
                                isLoading = false
                            }
                        }
                    } else {
                        isEditing = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isEditing) Color(0xFFC1272D) else Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(4.dp),
                border = if (!isEditing) BorderStroke(1.dp, Color(0xFFC1272D)) else null
            ) {
                Icon(
                    imageVector = if (isEditing) Icons.Default.Save else Icons.Default.Edit,
                    contentDescription = null,
                    tint = if (isEditing) Color.White else Color(0xFFC1272D),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isEditing) "Save Information" else "Edit Profile",
                    color = if (isEditing) Color.White else Color(0xFFC1272D),
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(onClick = onLogout) {
            Text("Logout", color = Color.Gray, fontFamily = Poppins)
        }
    }
}

@Composable
fun ProfileField(label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFF0F0F0))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 12.sp, fontFamily = Poppins)
            Text(value, color = Color.Black, fontSize = 16.sp, fontFamily = Poppins)
        }
    }
}

@Composable
fun EditableProfileField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontFamily = Poppins) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFC1272D),
            unfocusedBorderColor = Color(0xFFF0F0F0)
        )
    )
}

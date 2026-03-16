package com.example.flavyo.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.data.RetrofitClient
import com.example.flavyo.data.Userdata
import com.example.flavyo.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUsersScreen(onBack: () -> Unit) {
    var users by remember { mutableStateOf<List<Userdata>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            // Pass an empty map to satisfy the @Body requirement
            val response = RetrofitClient.authApi.getAllUsers(body = emptyMap())
            if (response.isSuccessful) {
                users = response.body() ?: emptyList()
            } else {
                errorMessage = "Server Error: ${response.code()}"
            }
        } catch (e: Exception) {
            errorMessage = "Check Internet / Deployment"
            Log.e("AdminUsersScreen", "Error", e)
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Profiles", fontFamily = Poppins, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding).background(Color.White)) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFFC1272D)
                )
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp),
                    fontFamily = Poppins
                )
            } else if (users.isEmpty()) {
                Text(
                    text = "No users found",
                    modifier = Modifier.align(Alignment.Center),
                    fontFamily = Poppins,
                    color = Color.Gray
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(users) { user ->
                        UserCard(user)
                    }
                }
            }
        }
    }
}

@Composable
fun UserCard(user: Userdata) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0ED))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = user.name ?: "No Name", fontWeight = FontWeight.Bold, fontFamily = Poppins, fontSize = 16.sp)
            Text(text = user.email ?: "No Email", fontSize = 14.sp, fontFamily = Poppins, color = Color.Gray)
            Text(text = user.address ?: "No Address", fontSize = 14.sp, fontFamily = Poppins, color = Color.Gray)
        }
    }
}

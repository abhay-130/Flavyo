package com.example.flavyo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.data.Userdata
import com.example.flavyo.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUsersScreen(onBack: () -> Unit) {
    // Mocking users since we don't have a specific API for list users yet
    val users = remember {
        listOf(
            Userdata("Abhay Kishor", "IIT Roorkee", "abhay@example.com"),
            Userdata("John Doe", "New York", "john@example.com")
        )
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
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).background(Color.White),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(users) { user ->
                UserCard(user)
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
            Text(text = user.name ?: "", fontWeight = FontWeight.Bold, fontFamily = Poppins, fontSize = 16.sp)
            Text(text = user.email ?: "", fontSize = 14.sp, fontFamily = Poppins, color = Color.Gray)
            Text(text = user.address ?: "", fontSize = 14.sp, fontFamily = Poppins, color = Color.Gray)
        }
    }
}

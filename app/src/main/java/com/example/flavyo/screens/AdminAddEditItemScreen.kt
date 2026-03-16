package com.example.flavyo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.flavyo.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAddEditItemScreen(
    onBack: () -> Unit
) {
    var itemName by remember { mutableStateOf("") }
    var itemPrice by remember { mutableStateOf("") }
    var itemDescription by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Item", fontFamily = Poppins, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = itemName,
                onValueChange = { itemName = it },
                label = { Text("Item Name", fontFamily = Poppins) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            OutlinedTextField(
                value = itemPrice,
                onValueChange = { itemPrice = it },
                label = { Text("Price (Rs.)", fontFamily = Poppins) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            OutlinedTextField(
                value = itemDescription,
                onValueChange = { itemDescription = it },
                label = { Text("Description", fontFamily = Poppins) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                minLines = 3
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* Add Item Logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC1272D)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Save Item", color = Color.White, fontWeight = FontWeight.Bold, fontFamily = Poppins)
            }
        }
    }
}

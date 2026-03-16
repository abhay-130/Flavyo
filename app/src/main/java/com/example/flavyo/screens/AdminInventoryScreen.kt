package com.example.flavyo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.data.IceCreamData
import com.example.flavyo.data.RetrofitClient
import com.example.flavyo.ui.theme.Poppins
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminInventoryScreen(onBack: () -> Unit) {
    val scope = rememberCoroutineScope()
    var items by remember { mutableStateOf<List<IceCreamData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                items = RetrofitClient.authApi.getInventory()
            } catch (e: Exception) {
                // Handle error
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inventory Management", fontFamily = Poppins, fontWeight = FontWeight.Bold) },
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
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color(0xFFC1272D))
            } else if (items.isEmpty()) {
                Text("No items in inventory", modifier = Modifier.align(Alignment.Center), fontFamily = Poppins)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items) { item ->
                        InventoryItemCard(item)
                    }
                }
            }
        }
    }
}

@Composable
fun InventoryItemCard(item: IceCreamData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0ED)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.name, fontWeight = FontWeight.Bold, fontFamily = Poppins, fontSize = 16.sp)
                Text(text = "Price: Rs. ${item.price}", color = Color(0xFFC1272D), fontWeight = FontWeight.SemiBold, fontFamily = Poppins)
            }
            IconButton(onClick = { /* Edit logic */ }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Gray)
            }
            IconButton(onClick = { /* Delete logic */ }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
            }
        }
    }
}

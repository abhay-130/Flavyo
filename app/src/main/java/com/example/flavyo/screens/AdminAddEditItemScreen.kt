package com.example.flavyo.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.data.RetrofitClient
import com.example.flavyo.ui.theme.Poppins
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAddEditItemScreen(onBack: () -> Unit) {
    // Row States
    var itemName by remember { mutableStateOf("") }
    var mrpPrice by remember { mutableStateOf("") }
    var salePrice by remember { mutableStateOf("") }
    var itemDescription by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var isSaving by remember { mutableStateOf(false) }

    // Image Picker Launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

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
                .verticalScroll(rememberScrollState()) // Allow scrolling for 5 rows
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Ice Cream Name
            OutlinedTextField(
                value = itemName,
                onValueChange = { itemName = it },
                label = { Text("Ice Cream Name", fontFamily = Poppins) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            // 2. MRP Price
            OutlinedTextField(
                value = mrpPrice,
                onValueChange = { mrpPrice = it },
                label = { Text("MRP (Rs.)", fontFamily = Poppins) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            // 3. Sale Price
            OutlinedTextField(
                value = salePrice,
                onValueChange = { salePrice = it },
                label = { Text("Price (Rs.)", fontFamily = Poppins) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            // 4. Description
            OutlinedTextField(
                value = itemDescription,
                onValueChange = { itemDescription = it },
                label = { Text("Description", fontFamily = Poppins) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                minLines = 3
            )

            // 5. Image Upload Row
            OutlinedButton(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.Gray)
            ) {
                Icon(Icons.Default.Image, contentDescription = null, tint = Color.Gray)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = if (selectedImageUri == null) "Upload Image" else "Image Selected ✅",
                    fontFamily = Poppins,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (isSaving) {
                CircularProgressIndicator(color = Color(0xFFC1272D))
            } else {
                Button(
                    onClick = {
                        if (itemName.isEmpty() || salePrice.isEmpty()) {
                            Toast.makeText(context, "Name and Price are required", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        isSaving = true
                        scope.launch {
                            try {
                                val response = RetrofitClient.authApi.addItem(
                                    itemData = mapOf(
                                        "name" to itemName,
                                        "mrp" to mrpPrice,
                                        "price" to salePrice,
                                        "description" to itemDescription,
                                        "image" to (selectedImageUri?.toString() ?: "no_image")
                                    )
                                )
                                if (response.isSuccessful) {
                                    Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                                    onBack()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                            } finally {
                                isSaving = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC1272D)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Save Item", color = Color.White, fontWeight = FontWeight.Bold, fontFamily = Poppins)
                }
            }
        }
    }
}
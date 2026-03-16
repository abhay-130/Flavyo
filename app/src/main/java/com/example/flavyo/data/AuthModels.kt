package com.example.flavyo.data

// The data we send to the sheet
data class AuthRequest(
    val name: String? = null,
    val emailOrPhone: String? = null,
    val address: String? = null,
    val password: String? = null,
    val userEmail: String? = null
)

data class AuthResponse(
    val status: String? = null,
    val result: String? = null,
    val message: String? = null,
    val name: String? = null,
    val email: String? = null,
    val address: String? = null,
    val user: Userdata? = null,
)

data class Userdata(
    val name: String? = null,
    val address: String? = null,
    val email: String? = null
)

// Data class for Order requests/responses
data class OrderRequest(
    val id: String?,
    val userEmail: String?,
    val timestamp: Long?,
    val totalAmount: Double?,
    val itemsString: String?
)

data class OrderResponse(
    val id: String? = null,
    val userEmail: String? = null,
    val timestamp: Long? = null,
    val totalAmount: Double? = null,
    val itemsString: String? = null,
    val status: String? = null
)

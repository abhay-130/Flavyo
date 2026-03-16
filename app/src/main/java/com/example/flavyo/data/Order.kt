package com.example.flavyo.data

import java.util.UUID

data class Order(
    val id: String = UUID.randomUUID().toString(),
    val items: Map<String, Int>, // IceCream ID to Quantity
    val timestamp: Long = System.currentTimeMillis(),
    val totalAmount: Int
)

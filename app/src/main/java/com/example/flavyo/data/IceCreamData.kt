package com.example.flavyo.data

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.flavyo.R

data class IceCreamData(
    val id: String,
    val name: String,
    val price: String,
    val imageResName: String
)

@SuppressLint("DiscouragedApi")
@Composable
fun getDrawableId(name: String): Int {
    val context = LocalContext.current
    val resourceId = context.resources.getIdentifier(name, "drawable", context.packageName)
    return if (resourceId != 0) resourceId else R.drawable.launch_logo
}

fun getMockData() = listOf(
    IceCreamData("1", "Cotton Candy", "35", "ic_ice_cream_placeholder"),
    IceCreamData("2", "Chocolate Cone", "35", "ic_ice_cream_placeholder"),
    IceCreamData("3", "Mango Kulfi", "35", "ic_ice_cream_placeholder")
)

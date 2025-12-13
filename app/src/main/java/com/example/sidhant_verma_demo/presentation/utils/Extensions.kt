package com.example.sidhant_verma_demo.presentation.utils

import java.text.NumberFormat
import java.util.Locale

fun Double.toRupee(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    val formatted = formatter.format(kotlin.math.abs(this))

    return if (this < 0) {
        "-₹${formatted.replace("₹", "").trim()}"
    } else {
        "₹$formatted".replace("₹", "").trim().let { "₹$it" }
    }
}

fun Double.toPercentage(): String {
    return String.format("%.2f%%", this)
}
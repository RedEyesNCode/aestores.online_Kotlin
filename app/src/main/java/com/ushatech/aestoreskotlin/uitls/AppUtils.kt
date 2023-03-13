package com.ushatech.aestoreskotlin.uitls

open class AppUtils {

    fun calcuateDiscount(markedprice: Double, s: Double): Double {
        return s * markedprice / 100
    }
}
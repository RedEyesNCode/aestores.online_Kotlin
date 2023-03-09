package com.ushatech.aestoreskotlin.data

import com.google.gson.annotations.SerializedName

data class CartDataRemote(
    @SerializedName("productId" ) var productId : Int? = null,
    @SerializedName("quantity"  ) var quantity  : Int? = null)

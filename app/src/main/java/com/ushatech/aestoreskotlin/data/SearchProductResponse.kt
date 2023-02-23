package com.ushatech.aestoreskotlin.data

import com.google.gson.annotations.SerializedName

data class SearchProductResponse(  @SerializedName("status"  ) var status  : Int?    = null,
                                   @SerializedName("message" ) var message : String? = null,
                                   @SerializedName("data"    ) var data    : Data?   = Data()){



    data class Products (

        @SerializedName("id"      ) var id     : String? = null,
        @SerializedName("name"    ) var name   : String? = null,
        @SerializedName("prod_id" ) var prodId : String? = null,
        @SerializedName("price"   ) var price  : String? = null,
        @SerializedName("mrp"     ) var mrp    : String? = null,
        @SerializedName("image"   ) var image  : String? = null

    )
    data class Data (

        @SerializedName("products"    ) var products    : ArrayList<Products> = arrayListOf(),
        @SerializedName("currentPage" ) var currentPage : String?             = null

    )
}

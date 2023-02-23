package com.ushatech.aestoreskotlin.data

import com.google.gson.annotations.SerializedName

data class HomeScreenResponse(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : Data?   = Data()
){
    data class Data (

        @SerializedName("slides"       ) var slides       : ArrayList<Slides>       = arrayListOf(),
        @SerializedName("arrival"      ) var arrival      : ArrayList<Arrival>      = arrayListOf(),
        @SerializedName("featured"     ) var featured     : ArrayList<String>       = arrayListOf(),
        @SerializedName("trending"     ) var trending     : ArrayList<Trending>     = arrayListOf(),
        @SerializedName("bestseller"   ) var bestseller   : ArrayList<String>       = arrayListOf(),
        @SerializedName("categories"   ) var categories   : ArrayList<Categories>   = arrayListOf(),
        @SerializedName("banners"      ) var banners      : ArrayList<Banners>      = arrayListOf(),
        @SerializedName("testimonials" ) var testimonials : ArrayList<Testimonials> = arrayListOf()

    )

    data class Slides (

        @SerializedName("image" ) var image : String? = null

    )
    data class Arrival (

        @SerializedName("id"      ) var id     : String? = null,
        @SerializedName("name"    ) var name   : String? = null,
        @SerializedName("prod_id" ) var prodId : String? = null,
        @SerializedName("price"   ) var price  : String? = null,
        @SerializedName("mrp"     ) var mrp    : String? = null,
        @SerializedName("image"   ) var image  : String? = null

    )
    data class Trending (

        @SerializedName("id"      ) var id     : String? = null,
        @SerializedName("name"    ) var name   : String? = null,
        @SerializedName("prod_id" ) var prodId : String? = null,
        @SerializedName("price"   ) var price  : String? = null,
        @SerializedName("mrp"     ) var mrp    : String? = null,
        @SerializedName("image"   ) var image  : String? = null

    )
    data class Categories (

        @SerializedName("id"            ) var id            : String? = null,
        @SerializedName("name"          ) var name          : String? = null,
        @SerializedName("image"         ) var image         : String? = null,
        @SerializedName("productCounts" ) var productCounts : Int?    = null

    )

    data class Testimonials (

        @SerializedName("username"    ) var username    : String? = null,
        @SerializedName("testimonial" ) var testimonial : String? = null,
        @SerializedName("designation" ) var designation : String? = null,
        @SerializedName("userimage"   ) var userimage   : String? = null

    )
    data class Products (

        @SerializedName("id"      ) var id     : String? = null,
        @SerializedName("name"    ) var name   : String? = null,
        @SerializedName("prod_id" ) var prodId : String? = null,
        @SerializedName("price"   ) var price  : String? = null,
        @SerializedName("mrp"     ) var mrp    : String? = null,
        @SerializedName("image"   ) var image  : String? = null
    )
    data class Banners (

        @SerializedName("category" ) var category : String?             = null,
        @SerializedName("image"    ) var image    : String?             = null,
        @SerializedName("link"     ) var link     : String?             = null,
        @SerializedName("products" ) var products : ArrayList<Products> = arrayListOf()

    )



}

package com.ushatech.aestoreskotlin.data

import com.google.gson.annotations.SerializedName

data class SubCateogryResponse(  @SerializedName("status"  ) var status  : Int?            = null,
                                 @SerializedName("message" ) var message : String?         = null,
                                 @SerializedName("data"    ) var data    : ArrayList<Data> = arrayListOf()){
    data class Data (

        @SerializedName("id"   ) var id   : String? = null,
        @SerializedName("name" ) var name : String? = null

    )
}

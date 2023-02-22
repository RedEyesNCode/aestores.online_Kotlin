package com.ushatech.aestoreskotlin.data

import com.google.gson.annotations.SerializedName

data class AllCategoryResponse(@SerializedName("status"  ) var status  : Int?            = null,
                               @SerializedName("message" ) var message : String?         = null,
                               @SerializedName("data"    ) var data    : ArrayList<Data> = arrayListOf()) {

    data class Supercategories (

        @SerializedName("id"   ) var id   : String? = null,
        @SerializedName("name" ) var name : String? = null

    )
    data class Subcategories (

        @SerializedName("id"              ) var id              : String?                    = null,
        @SerializedName("name"            ) var name            : String?                    = null,
        @SerializedName("supercategories" ) var supercategories : ArrayList<Supercategories> = arrayListOf()

    )
    data class Data (

        @SerializedName("id"            ) var id            : String?                  = null,
        @SerializedName("name"          ) var name          : String?                  = null,
        @SerializedName("image"         ) var image         : String?                  = null,
        @SerializedName("subcategories" ) var subcategories : ArrayList<Subcategories> = arrayListOf()

    )
}
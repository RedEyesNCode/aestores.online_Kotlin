package com.ushatech.aestoreskotlin.data

import com.google.gson.annotations.SerializedName

data class ProductDetailResponseData(@SerializedName("status"  ) var status  : Int?    = null,
                                     @SerializedName("message" ) var message : String? = null,
                                     @SerializedName("data"    ) var data    : Data?   = Data()
){
    data class MoreImages (

        @SerializedName("image" ) var image : String? = null

    )
    data class SimilarProducts (

        @SerializedName("id"      ) var id     : String? = null,
        @SerializedName("name"    ) var name   : String? = null,
        @SerializedName("prod_id" ) var prodId : String? = null,
        @SerializedName("price"   ) var price  : String? = null,
        @SerializedName("mrp"     ) var mrp    : String? = null,
        @SerializedName("image"   ) var image  : String? = null

    )

    data class Data (

        @SerializedName("id"                  ) var id                  : String?                    = null,
        @SerializedName("prod_id"             ) var prodId              : String?                    = null,
        @SerializedName("name"                ) var name                : String?                    = null,
        @SerializedName("subtitle"            ) var subtitle            : String?                    = null,
        @SerializedName("image"               ) var image               : String?                    = null,
        @SerializedName("description"         ) var description         : String?                    = null,
        @SerializedName("hsn_code"            ) var hsnCode             : String?                    = null,
        @SerializedName("key_ingredient"      ) var keyIngredient       : String?                    = null,
        @SerializedName("how_to_use"          ) var howToUse            : String?                    = null,
        @SerializedName("price"               ) var price               : String?                    = null,
        @SerializedName("mrp"                 ) var mrp                 : String?                    = null,
        @SerializedName("skucode"             ) var skucode             : String?                    = null,
        @SerializedName("weight"              ) var weight              : String?                    = null,
        @SerializedName("stock"               ) var stock               : String?                    = null,
        @SerializedName("featured"            ) var featured            : String?                    = null,
        @SerializedName("youtubeurl"          ) var youtubeurl          : String?                    = null,
        @SerializedName("trending"            ) var trending            : String?                    = null,
        @SerializedName("rc"                  ) var rc                  : String?                    = null,
        @SerializedName("avgRating"           ) var avgRating           : String?                    = null,
        @SerializedName("ratingCount"         ) var ratingCount         : Int?                       = null,
        @SerializedName("reviewCount"         ) var reviewCount         : Int?                       = null,
        @SerializedName("rating1PercentCount" ) var rating1PercentCount : Int?                       = null,
        @SerializedName("rating2PercentCount" ) var rating2PercentCount : Int?                       = null,
        @SerializedName("rating3percentCount" ) var rating3percentCount : Int?                       = null,
        @SerializedName("rating4PercentCount" ) var rating4PercentCount : Int?                       = null,
        @SerializedName("rating5PercentCount" ) var rating5PercentCount : Int?                       = null,
        @SerializedName("moreImages"          ) var moreImages          : ArrayList<MoreImages>      = arrayListOf(),
        @SerializedName("variations"          ) var variations          : ArrayList<String>          = arrayListOf(),
        @SerializedName("features"            ) var features            : ArrayList<String>          = arrayListOf(),
        @SerializedName("reviews"             ) var reviews             : ArrayList<String>          = arrayListOf(),
        @SerializedName("similarProducts"     ) var similarProducts     : ArrayList<SimilarProducts> = arrayListOf(),
        @SerializedName("reviewEligibility"   ) var reviewEligibility   : Int?                       = null

    )
}

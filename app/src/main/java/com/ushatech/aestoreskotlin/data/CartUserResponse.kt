package com.ushatech.aestoreskotlin.data

import com.google.gson.annotations.SerializedName

data class CartUserResponse(@SerializedName("items"             ) var items           : ArrayList<Items> = arrayListOf(),
                            @SerializedName("total"             ) var total           : Int?             = null,
                            @SerializedName("total_price"       ) var totalPrice      : Int?             = null,
                            @SerializedName("total_tax"         ) var totalTax        : Int?             = null,
                            @SerializedName("total_shipping"    ) var totalShipping   : Int?             = null,
                            @SerializedName("total_shipping_ck" ) var totalShippingCk : Int?             = null,
                            @SerializedName("loyalty_points"    ) var loyaltyPoints   : Int?             = null,
                            @SerializedName("max_redeem_bonus"  ) var maxRedeemBonus  : Int?             = null,
                            @SerializedName("count"             ) var count           : Int?             = null){

    data class Items (

        @SerializedName("id"               ) var id             : String? = null,
        @SerializedName("product_id"       ) var productId      : String? = null,
        @SerializedName("quantity"         ) var quantity       : String? = null,
        @SerializedName("userid"           ) var userid         : String? = null,
        @SerializedName("datetime"         ) var datetime       : String? = null,
        @SerializedName("name"             ) var name           : String? = null,
        @SerializedName("image"            ) var image          : String? = null,
        @SerializedName("price"            ) var price          : String? = null,
        @SerializedName("weight"           ) var weight         : String? = null,
        @SerializedName("max_redeem_bonus" ) var maxRedeemBonus : String? = null,
        @SerializedName("loyalty_points"   ) var loyaltyPoints  : String? = null,
        @SerializedName("shippingPrice"    ) var shippingPrice  : String? = null,
        @SerializedName("gst"              ) var gst            : String? = null,
        @SerializedName("sgst"             ) var sgst           : String? = null,
        @SerializedName("igst"             ) var igst           : String? = null,
        @SerializedName("tax"              ) var tax            : Int?    = null,
        @SerializedName("total_price"      ) var totalPrice     : Int?    = null,
        @SerializedName("cart_id"          ) var cartId         : String? = null,

    )

}

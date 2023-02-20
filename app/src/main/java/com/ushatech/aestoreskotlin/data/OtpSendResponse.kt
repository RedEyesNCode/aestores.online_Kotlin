package com.ushatech.aestoreskotlin.data

import com.google.gson.annotations.SerializedName

data class OtpSendResponse(@SerializedName("status"  ) var status  : Int?    = null,
                           @SerializedName("message" ) var message : String? = null,
                           @SerializedName("data"    ) var data    : Data?   = Data()){
    data class Data (

        @SerializedName("userid" ) var userid : Int? = null

    )
}

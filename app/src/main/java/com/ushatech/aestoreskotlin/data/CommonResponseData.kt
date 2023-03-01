package com.ushatech.aestoreskotlin.data

import com.google.gson.annotations.SerializedName

data class CommonResponseData(@SerializedName("status"  ) var status  : Int?    = null,
                              @SerializedName("message" ) var message : String? = null)

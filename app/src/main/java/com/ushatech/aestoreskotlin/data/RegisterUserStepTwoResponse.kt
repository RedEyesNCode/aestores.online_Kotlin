package com.ushatech.aestoreskotlin.data

import com.google.gson.annotations.SerializedName

data class RegisterUserStepTwoResponse( @SerializedName("status"  ) var status  : Int?    = null,
                                        @SerializedName("message" ) var message : String? = null,
                                        @SerializedName("data"    ) var data    : Data?   = Data()){

    data class Data (

        @SerializedName("id"               ) var id             : String? = null,
        @SerializedName("name"             ) var name           : String? = null,
        @SerializedName("email"            ) var email          : String? = null,
        @SerializedName("mobile"           ) var mobile         : String? = null,
        @SerializedName("age"              ) var age            : String? = null,
        @SerializedName("gender"           ) var gender         : String? = null,
        @SerializedName("street"           ) var street         : String? = null,
        @SerializedName("address"          ) var address        : String? = null,
        @SerializedName("city"             ) var city           : String? = null,
        @SerializedName("state"            ) var state          : String? = null,
        @SerializedName("pincode"          ) var pincode        : String? = null,
        @SerializedName("country"          ) var country        : String? = null,
        @SerializedName("otp"              ) var otp            : String? = null,
        @SerializedName("created"          ) var created        : String? = null,
        @SerializedName("referral"         ) var referral       : String? = null,
        @SerializedName("referred_by"      ) var referredBy     : String? = null,
        @SerializedName("pancard"          ) var pancard        : String? = null,
        @SerializedName("aadhar_card"      ) var aadharCard     : String? = null,
        @SerializedName("wallet"           ) var wallet         : String? = null,
        @SerializedName("image"            ) var image          : String? = null,
        @SerializedName("account_name"     ) var accountName    : String? = null,
        @SerializedName("bank_name"        ) var bankName       : String? = null,
        @SerializedName("account_number"   ) var accountNumber  : String? = null,
        @SerializedName("ifsc_code"        ) var ifscCode       : String? = null,
        @SerializedName("status"           ) var status         : String? = null,
        @SerializedName("deleted"          ) var deleted        : String? = null,
        @SerializedName("aadhar_front_img" ) var aadharFrontImg : String? = null,
        @SerializedName("aadhar_back_img"  ) var aadharBackImg  : String? = null,
        @SerializedName("pancard_img"      ) var pancardImg     : String? = null,
        @SerializedName("parent_id"        ) var parentId       : String? = null,
        @SerializedName("placement"        ) var placement      : String? = null,
        @SerializedName("sponser_id"       ) var sponserId      : String? = null,
        @SerializedName("kyc_status"       ) var kycStatus      : String? = null

    )
}

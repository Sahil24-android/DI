package com.event.eventmanagement.views.auth.datasource

import com.google.gson.annotations.SerializedName

data class PinCodeData(
    @SerializedName("Message"    ) var Message    : String?               = null,
    @SerializedName("Status"     ) var Status     : String?               = null,
    @SerializedName("PostOffice" ) var PostOffice : ArrayList<PostOffice> = arrayListOf()
)

data class PostOffice (

    @SerializedName("Name"           ) var Name           : String? = null,
    @SerializedName("Description"    ) var Description    : String? = null,
    @SerializedName("BranchType"     ) var BranchType     : String? = null,
    @SerializedName("DeliveryStatus" ) var DeliveryStatus : String? = null,
    @SerializedName("Circle"         ) var Circle         : String? = null,
    @SerializedName("District"       ) var District       : String? = null,
    @SerializedName("Division"       ) var Division       : String? = null,
    @SerializedName("Region"         ) var Region         : String? = null,
    @SerializedName("Block"          ) var Block          : String? = null,
    @SerializedName("State"          ) var State          : String? = null,
    @SerializedName("Country"        ) var Country        : String? = null,
    @SerializedName("Pincode"        ) var Pincode        : String? = null

)

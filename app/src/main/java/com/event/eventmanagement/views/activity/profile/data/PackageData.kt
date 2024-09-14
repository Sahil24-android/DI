package com.event.eventmanagement.views.activity.profile.data

import com.google.gson.annotations.SerializedName

data class BuyPackageBody(
    @SerializedName("package_id"       ) var packageId       : Int?    = null,
    @SerializedName("vendor_id"        ) var vendorId        : Int?    = null,
    @SerializedName("package_validity" ) var packageValidity : Int?    = null,
    @SerializedName("amount"           ) var amount          : String? = null,
    @SerializedName("purchase_date"    ) var purchaseDate    : String? = null,
    @SerializedName("transaction_id"   ) var transactionId   : String? = null
)
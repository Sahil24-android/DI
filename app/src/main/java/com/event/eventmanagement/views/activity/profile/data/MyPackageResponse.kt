package com.event.eventmanagement.views.activity.profile.data

import com.google.gson.annotations.SerializedName

data class MyPackageResponse(
    @SerializedName("msg"             ) var msg             : String?                    = null,
    @SerializedName("packagePurchase" ) var packagePurchase : ArrayList<PackagePurchase> = arrayListOf()
){
    data class PackagePurchase (

        @SerializedName("id"               ) var id              : Int?                     = null,
        @SerializedName("package_id"       ) var packageId       : Int?                     = null,
        @SerializedName("vendor_id"        ) var vendorId        : Int?                     = null,
        @SerializedName("package_validity" ) var packageValidity : String?                  = null,
        @SerializedName("amount"           ) var amount          : String?                  = null,
        @SerializedName("purchase_date"    ) var purchaseDate    : String?                  = null,
        @SerializedName("transaction_id"   ) var transactionId   : String?                  = null,
        @SerializedName("is_active"        ) var isActive        : Int?                     = null,
        @SerializedName("is_delete"        ) var isDelete        : Int?                     = null,
        @SerializedName("createdAt"        ) var createdAt       : String?                  = null,
        @SerializedName("updatedAt"        ) var updatedAt       : String?                  = null,
        @SerializedName("package_detail"   ) var packageDetail   : ArrayList<PackageDetail> = arrayListOf()

    ){

        data class PackageDetail (

            @SerializedName("id"               ) var id             : Int?    = null,
            @SerializedName("service_id"       ) var serviceId      : Int?    = null,
            @SerializedName("package_name"     ) var packageName    : String? = null,
            @SerializedName("description"      ) var description    : String? = null,
            @SerializedName("validity_in_days" ) var validityInDays : Int?    = null,
            @SerializedName("amount"           ) var amount         : Int?    = null,
            @SerializedName("is_active"        ) var isActive       : Int?    = null,
            @SerializedName("is_delete"        ) var isDelete       : Int?    = null,
            @SerializedName("createdAt"        ) var createdAt      : String? = null,
            @SerializedName("updatedAt"        ) var updatedAt      : String? = null

        )
    }
}
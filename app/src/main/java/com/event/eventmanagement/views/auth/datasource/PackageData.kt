package com.event.eventmanagement.views.auth.datasource

import com.google.gson.annotations.SerializedName

data class PackageData(
    @SerializedName("data" ) var data : ArrayList<PackageItem> = arrayListOf()

)
data class PackageItem (

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

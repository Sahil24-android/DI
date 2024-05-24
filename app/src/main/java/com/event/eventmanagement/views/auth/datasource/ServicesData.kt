package com.event.eventmanagement.views.auth.datasource

import com.google.gson.annotations.SerializedName

data class ServicesData(
    @SerializedName("data" ) var data : ArrayList<Data> = arrayListOf()
)

data class Data (

    @SerializedName("id"           ) var id          : Int?    = null,
    @SerializedName("service_name" ) var serviceName : String? = null,
    @SerializedName("description"  ) var description : String? = null,
    @SerializedName("is_active"    ) var isActive    : Int?    = null,
    @SerializedName("is_delete"    ) var isDelete    : Int?    = null,
    @SerializedName("createdAt"    ) var createdAt   : String? = null,
    @SerializedName("updatedAt"    ) var updatedAt   : String? = null

)
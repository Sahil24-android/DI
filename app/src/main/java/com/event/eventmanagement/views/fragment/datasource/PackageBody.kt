package com.event.eventmanagement.views.fragment.datasource

import com.google.gson.annotations.SerializedName

data class PackageBody(
    @SerializedName("description"  ) var description : String? = null,
    @SerializedName("package_name" ) var packageName : String? = null,
    @SerializedName("event_id"     ) var eventId     : String? = null,
    @SerializedName("amount"       ) var amount      : Int?    = null
)

data class PackageResponse(
    @SerializedName("msg"          ) var msg          : String?       = null,
    @SerializedName("EventPackage" ) var EventPackage : EventPackage? = EventPackage()
)

data class EventPackage (

    @SerializedName("id"           ) var id          : Int?    = null,
    @SerializedName("description"  ) var description : String? = null,
    @SerializedName("package_name" ) var packageName : String? = null,
    @SerializedName("event_id"     ) var eventId     : String? = null,
    @SerializedName("amount"       ) var amount      : Int?    = null,
    @SerializedName("updatedAt"    ) var updatedAt   : String? = null,
    @SerializedName("createdAt"    ) var createdAt   : String? = null

)

data class AllPackageResponse(
    @SerializedName("data" ) var data : ArrayList<EventPackageResponse> = arrayListOf()

)


data class EventPackageResponse (

    @SerializedName("id"           ) var id          : Int?              = null,
    @SerializedName("event_id"     ) var eventId     : String?           = null,
    @SerializedName("package_name" ) var packageName : String?           = null,
    @SerializedName("description"  ) var description : String?           = null,
    @SerializedName("amount"       ) var amount      : Int?              = null,
    @SerializedName("is_active"    ) var isActive    : Int?              = null,
    @SerializedName("is_delete"    ) var isDelete    : Int?              = null,
    @SerializedName("createdAt"    ) var createdAt   : String?           = null,
    @SerializedName("updatedAt"    ) var updatedAt   : String?           = null,
    @SerializedName("newKey"       ) var newKey      : ArrayList<NewKey> = arrayListOf()

)


data class NewKey (

    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("vendor_id"   ) var vendorId    : Int?    = null,
    @SerializedName("event_name"  ) var eventName   : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("is_active"   ) var isActive    : Int?    = null,
    @SerializedName("is_delete"   ) var isDelete    : Int?    = null,
    @SerializedName("createdAt"   ) var createdAt   : String? = null,
    @SerializedName("updatedAt"   ) var updatedAt   : String? = null

)
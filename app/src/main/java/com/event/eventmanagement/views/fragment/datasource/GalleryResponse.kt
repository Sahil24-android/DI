package com.event.eventmanagement.views.fragment.datasource

import com.google.gson.annotations.SerializedName

data class GalleryResponse(

    @SerializedName("msg"    ) var msg    : String?           = null,
    @SerializedName("vendor" ) var vendor : ArrayList<GalleryItem> = arrayListOf()
)

data class GalleryItem(
    @SerializedName("id"        ) var id        : Int?    = null,
    @SerializedName("vendor_id" ) var vendorId  : Int?    = null,
    @SerializedName("event_id"  ) var eventId   : Int?    = null,
    @SerializedName("images"    ) var images    : String? = null,
    @SerializedName("is_active" ) var isActive  : Int?    = null,
    @SerializedName("is_delete" ) var isDelete  : Int?    = null,
    @SerializedName("createdAt" ) var createdAt : String? = null,
    @SerializedName("updatedAt" ) var updatedAt : String? = null
)

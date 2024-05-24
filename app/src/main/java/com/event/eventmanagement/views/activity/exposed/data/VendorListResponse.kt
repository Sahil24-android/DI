package com.event.eventmanagement.views.activity.exposed.data

import com.event.eventmanagement.views.auth.datasource.Vendor
import com.google.gson.annotations.SerializedName

data class VendorListResponse(
    @SerializedName("msg") var msg: String? = null,
    @SerializedName("vendor") var vendor: ArrayList<Vendor> = arrayListOf()
)

data class ExposedBody(
    @SerializedName("event_manage_id") var eventManageId: Int? = null,
    @SerializedName("vendor_from") var vendorFrom: Int? = null,
    @SerializedName("vendor_to") var vendorTo: Int? = null,
    @SerializedName("original_event_amount") var originalEventAmount: Int? = null,
    @SerializedName("exposing_amount") var exposingAmount: Int? = null
)

data class ExposedResponse(

    @SerializedName("msg") var msg: String? = null,
    @SerializedName("Transfer") var Transfer: Transfer? = Transfer()
)


data class Transfer(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("event_manage_id") var eventManageId: Int? = null,
    @SerializedName("vendor_from") var vendorFrom: Int? = null,
    @SerializedName("vendor_to") var vendorTo: Int? = null,
    @SerializedName("original_event_amount") var originalEventAmount: Int? = null,
    @SerializedName("exposing_amount") var exposingAmount: Int? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("createdAt") var createdAt: String? = null

)
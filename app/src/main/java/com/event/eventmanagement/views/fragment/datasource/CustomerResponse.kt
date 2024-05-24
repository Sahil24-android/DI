package com.event.eventmanagement.views.fragment.datasource

import com.google.gson.annotations.SerializedName

data class CustomerResponse(
    @SerializedName("data") var data: ArrayList<CustomerDetails> = arrayListOf()

)


data class CustomerDetails(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("vendor_id") var vendorId: Int? = null,
    @SerializedName("customer_name") var customerName: String? = null,
    @SerializedName("dob") var dob: String? = null,
    @SerializedName("anniversary_date") var anniversaryDate: String? = null,
    @SerializedName("mob_no") var mobNo: String? = null,
    @SerializedName("alt_mob_no") var altMobNo: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("country_id") var countryId: String? = null,
    @SerializedName("pincode") var pincode: Int? = null,
    @SerializedName("state_id") var stateId: String? = null,
    @SerializedName("city_id") var cityId: String? = null,
    @SerializedName("is_active") var isActive: Int? = null,
    @SerializedName("is_delete") var isDelete: Int? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null
)
package com.event.eventmanagement.views.auth.datasource

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("msg"   ) var msg   : String? = null,
    @SerializedName("token" ) var token : String? = null,
    @SerializedName("data"  ) var data  : DataUser?   = DataUser()
)

data class DataUser (

    @SerializedName("id"             ) var id           : Int?    = null,
    @SerializedName("service_id"     ) var serviceId    : Int?    = null,
    @SerializedName("service_pkg_id" ) var servicePkgId : Int?    = null,
    @SerializedName("company_name"   ) var companyName  : String? = null,
    @SerializedName("owner_name"     ) var ownerName    : String? = null,
    @SerializedName("logo_image"     ) var logoImage    : String? = null,
    @SerializedName("mob_no"         ) var mobNo        : String? = null,
    @SerializedName("alt_mob_no"     ) var altMobNo     : String? = null,
    @SerializedName("password"       ) var password     : String? = null,
    @SerializedName("address"        ) var address      : String? = null,
    @SerializedName("pincode"        ) var pincode      : Int?    = null,
    @SerializedName("location_id"    ) var locationId   : Int?    = null,
    @SerializedName("country_id"     ) var countryId    : String? = null,
    @SerializedName("state_id"       ) var stateId      : String? = null,
    @SerializedName("city_id"        ) var cityId       : String? = null,
    @SerializedName("is_active"      ) var isActive     : Int?    = null,
    @SerializedName("is_delete"      ) var isDelete     : Int?    = null,
    @SerializedName("expiry_date"    ) var expiryDate   : String? = null,
    @SerializedName("createdAt"      ) var createdAt    : String? = null,
    @SerializedName("updatedAt"      ) var updatedAt    : String? = null

)

data class LoginBody(
    @SerializedName("mobile_no") val mobileNumber: String,
    @SerializedName("password") val password: String

)

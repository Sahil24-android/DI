package com.event.eventmanagement.views.auth.datasource

import com.google.gson.annotations.SerializedName

data class RegisterUserResponse(
    @SerializedName("msg"    ) var msg    : String? = null,
    @SerializedName("vendor" ) var vendor : Vendor? = Vendor()
)

data class Vendor (

    @SerializedName("id"             ) var id           : Int?    = null,
    @SerializedName("service_id"     ) var serviceId    : String? = null,
    @SerializedName("service_pkg_id" ) var servicePkgId : String? = null,
    @SerializedName("company_name"   ) var companyName  : String? = null,
    @SerializedName("owner_name"     ) var ownerName    : String? = null,
    @SerializedName("mob_no"         ) var mobNo        : String? = null,
    @SerializedName("alt_mob_no"     ) var altMobNo     : String? = null,
    @SerializedName("address"        ) var address      : String? = null,
    @SerializedName("pincode"        ) var pincode      : String? = null,
    @SerializedName("location_id"    ) var locationId   : String? = null,
    @SerializedName("country_id"     ) var countryId    : String? = null,
    @SerializedName("state_id"       ) var stateId      : String? = null,
    @SerializedName("is_active"      ) var isActive     : String? = null,
    @SerializedName("city_id"        ) var cityId       : String? = null,
    @SerializedName("logo_image"     ) var logoImage    : String? = null,
    @SerializedName("expiry_date"    ) var expiryDate   : String? = null,
    @SerializedName("updatedAt"      ) var updatedAt    : String? = null,
    @SerializedName("createdAt"      ) var createdAt    : String? = null

)

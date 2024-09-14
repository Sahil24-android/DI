package com.event.eventmanagement.views.activity.addEmployee

import com.google.gson.annotations.SerializedName

data class EmployeeBody(
    @SerializedName("id"               ) var id              : Int? = null,
    @SerializedName("vendor_id"        ) var vendorId        : String? = null,
    @SerializedName("employee_name"    ) var employeeName    : String? = null,
    @SerializedName("dob"              ) var dob             : String? = null,
    @SerializedName("anniversary_date" ) var anniversaryDate : String? = null,
    @SerializedName("adhar_no"         ) var adharNo         : String? = null,
    @SerializedName("mob_no"           ) var mobNo           : String? = null,
    @SerializedName("alt_mob_no"       ) var altMobNo        : String? = null,
    @SerializedName("address"          ) var address         : String? = null,
    @SerializedName("pincode"          ) var pincode         : String? = null,
    @SerializedName("country_id"       ) var countryId       : String? = null,
    @SerializedName("state_id"         ) var stateId         : String? = null,
    @SerializedName("city_id"          ) var cityId          : String? = null,
    @SerializedName("salary_type"      ) var salaryType      : String? = null,
    @SerializedName("salary_amount"    ) var salaryAmount    : String? = null,
    @SerializedName("bank_name"        ) var bankName        : String? = null,
    @SerializedName("account_no"       ) var accountNo       : String? = null,
    @SerializedName("ifsc_code"        ) var ifscCode        : String? = null,
    @SerializedName("branch_name"      ) var branchName      : String? = null
)

data class AddEmployeeResponse(
    @SerializedName("msg"      ) var msg      : String?   = null,
    @SerializedName("employee" ) var employee : EmployeeBody? = EmployeeBody()
)

data class GetEmployeeResponse(
    @SerializedName("data" ) var data : ArrayList<EmployeeBody> = arrayListOf()
)

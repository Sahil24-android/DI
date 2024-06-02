package com.event.eventmanagement.views.fragment.datasource

import com.event.eventmanagement.views.auth.datasource.Vendor
import com.google.gson.annotations.SerializedName

data class GetVendorExpenseResponse(
    @SerializedName("data" ) var data : ArrayList<ExpenseData> = arrayListOf()
)

data class ExpenseData(
    @SerializedName("id"                ) var id              : Int?    = null,
    @SerializedName("vendor_id"         ) var vendorId        : Int?    = null,
    @SerializedName("expense_name"      ) var expenseName     : String? = null,
    @SerializedName("description"       ) var description     : String? = null,
    @SerializedName("expense_to_whom"   ) var expenseToWhom   : String? = null,
    @SerializedName("expense_to_vendor" ) var expenseToVendor : Int?    = null,
    @SerializedName("event_manage_id"   ) var eventManageId   : Int?    = null,
    @SerializedName("employee_id"       ) var employeeId      : Int?    = null,
    @SerializedName("remaining_amount"  ) var remainingAmount : Int?    = null,
    @SerializedName("amount"            ) var amount          : Int?    = null,
    @SerializedName("is_active"         ) var isActive        : Int?    = null,
    @SerializedName("is_delete"         ) var isDelete        : Int?    = null,
    @SerializedName("createdAt"         ) var createdAt       : String? = null,
    @SerializedName("updatedAt"         ) var updatedAt       : String? = null,
    @SerializedName("vendor"            ) var vendor          : Vendor? = Vendor()
)



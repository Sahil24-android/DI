package com.event.eventmanagement.views.activity.vendorExpense.data

import com.google.gson.annotations.SerializedName

data class VendorExpenseResponse(
    @SerializedName("msg"     ) var msg     : String?  = null,
    @SerializedName("expense" ) var expense : Expense? = Expense()
)


data class Expense (

    @SerializedName("id"                ) var id              : Int?    = null,
    @SerializedName("vendor_id"         ) var vendorId        : Int?    = null,
    @SerializedName("expense_name"      ) var expenseName     : String? = null,
    @SerializedName("description"       ) var description     : String? = null,
    @SerializedName("expense_to_vendor" ) var expenseToVendor : String? = null,
    @SerializedName("event_manage_id"   ) var eventManageId   : Int?    = null,
    @SerializedName("amount"            ) var amount          : Int?    = null,
    @SerializedName("remaining_amount"  ) var remainingAmount : Int?    = null,
    @SerializedName("updatedAt"         ) var updatedAt       : String? = null,
    @SerializedName("createdAt"         ) var createdAt       : String? = null

)

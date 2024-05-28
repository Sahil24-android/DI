package com.event.eventmanagement.views.activity.vendorExpense.data

import com.google.gson.annotations.SerializedName
import java.time.temporal.TemporalAmount

data class VendorExpenseBody(
    @SerializedName("vendor_id"         ) var vendorId        : Int?    = null,
    @SerializedName("expense_name"      ) var expenseName     : String? = null,
    @SerializedName("description"       ) var description     : String? = null,
    @SerializedName("expense_to_vendor" ) var expenseToVendor : String? = null,
    @SerializedName("event_manage_id"   ) var eventManageId   : Int?    = null,
    @SerializedName("amount"            ) var amount          : Int?    = null,
    @SerializedName("remaining_amount"  ) var remainingAmount : Int?    = null
)

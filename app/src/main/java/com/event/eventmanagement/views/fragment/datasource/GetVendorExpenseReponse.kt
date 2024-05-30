package com.event.eventmanagement.views.fragment.datasource

import com.event.eventmanagement.views.activity.customerEventList.data.CustomerData
import com.event.eventmanagement.views.activity.customerEventList.data.ExpensePayment
import com.google.gson.annotations.SerializedName

data class GetVendorExpenseReponse(
    @SerializedName("data" ) var data : ArrayList<ExpenseData> = arrayListOf()
)

data class ExpenseData(
    @SerializedName("id"               ) var id              : Int?                      = null,
    @SerializedName("vendor_id"        ) var vendorId        : Int?                      = null,
    @SerializedName("event_pkg_id"     ) var eventPkgId      : Int?                      = null,
    @SerializedName("customer_id"      ) var customerId      : Int?                      = null,
    @SerializedName("description"      ) var description     : String?                   = null,
    @SerializedName("amount"           ) var amount          : Int?                      = null,
    @SerializedName("discount"         ) var discount        : Int?                      = null,
    @SerializedName("final_amount"     ) var finalAmount     : Int?                      = null,
    @SerializedName("advance_amount"   ) var advanceAmount   : Int?                      = null,
    @SerializedName("remaining_amount" ) var remainingAmount : Int?                      = null,
    @SerializedName("event_address"    ) var eventAddress    : String?                   = null,
    @SerializedName("is_active"        ) var isActive        : Int?                      = null,
    @SerializedName("is_delete"        ) var isDelete        : Int?                      = null,
    @SerializedName("createdAt"        ) var createdAt       : String?                   = null,
    @SerializedName("updatedAt"        ) var updatedAt       : String?                   = null,
    @SerializedName("customerdata"     ) var customerdata    : CustomerData?             = CustomerData(),
    @SerializedName("expense_payment"  ) var expensePayment  : ArrayList<ExpensePayment> = arrayListOf()
)



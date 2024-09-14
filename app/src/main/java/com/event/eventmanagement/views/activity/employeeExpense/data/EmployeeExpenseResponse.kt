package com.event.eventmanagement.views.activity.employeeExpense.data

import com.google.gson.annotations.SerializedName

data class EmployeeExpenseResponse(
    @SerializedName("msg"     ) var msg     : String?  = null,
    @SerializedName("expense" ) var expense : Expense? = Expense()
)

data class Expense (

    @SerializedName("id"          ) var id         : Int?    = null,
    @SerializedName("employee_id" ) var employeeId : Int?    = null,
    @SerializedName("amount"      ) var amount     : Int?    = null,
    @SerializedName("updatedAt"   ) var updatedAt  : String? = null,
    @SerializedName("createdAt"   ) var createdAt  : String? = null

)
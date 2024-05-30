package com.event.eventmanagement.views.fragment.datasource

import com.google.gson.annotations.SerializedName

data class GetReportResponse(
    @SerializedName("data" ) var data : Data? = Data()

)


data class Data (

    @SerializedName("sum_expense" ) var sumExpense : Int? = null,
    @SerializedName("sum_pay"     ) var sumPay     : Int? = null

)
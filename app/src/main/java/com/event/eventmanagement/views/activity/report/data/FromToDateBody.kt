package com.event.eventmanagement.views.activity.report.data

import com.google.gson.annotations.SerializedName

data class FromToDateBody(
    @SerializedName("from_date") val fromDate: String,
    @SerializedName("to_date") val toDate: String
)

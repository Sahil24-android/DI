package com.event.eventmanagement.views.activity.invoice.data

import com.google.gson.annotations.SerializedName

data class PdfBody(
    @SerializedName("event_pay_id") val eventPayId:String,
    @SerializedName("pdf_url") val pdfUrl:String,
    @SerializedName("pdf_name") val pdfName:String
)

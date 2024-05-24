package com.event.eventmanagement.views.activity.addeventPayments

import com.google.gson.annotations.SerializedName

data class PaymentBody(
    @SerializedName("event_manage_id"  ) var eventManageId   : Int?    = null,
    @SerializedName("paid_amount"      ) var paidAmount      : Int?    = null,
    @SerializedName("remaining_amount" ) var remainingAmount : Int?    = null,
    @SerializedName("paid_date"        ) var paidDate        : String? = null,
    @SerializedName("pdf_url") var pdfUrl:String? = null
)

data class PaymentResponse(
    @SerializedName("msg"   ) var msg   : String? = null,
    @SerializedName("Event" ) var Event : Event?  = Event()
)

data class Event (

    @SerializedName("id"               ) var id              : Int?    = null,
    @SerializedName("event_manage_id"  ) var eventManageId   : Int?    = null,
    @SerializedName("paid_amount"      ) var paidAmount      : Int?    = null,
    @SerializedName("remaining_amount" ) var remainingAmount : Int?    = null,
    @SerializedName("paid_date"        ) var paidDate        : String? = null,
    @SerializedName("updatedAt"        ) var updatedAt       : String? = null,
    @SerializedName("createdAt"        ) var createdAt       : String? = null

)
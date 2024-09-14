package com.event.eventmanagement.views.activity.profile.data

import com.google.gson.annotations.SerializedName

data class CheckTransactionStatus(
    @SerializedName("success")
    val success: Boolean? = null,

    @SerializedName("code")
    val code: String? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("data")
    val data: Data? = null
) {
    data class Data(
        @SerializedName("merchantId")
        val merchantId: String? = null,

        @SerializedName("merchantTransactionId")
        val merchantTransactionId: String? = null,

        @SerializedName("transactionId")
        val transactionId: String? = null,

        @SerializedName("amount")
        val amount: Long? = null,

        @SerializedName("state")
        val state: String? = null,

        @SerializedName("responseCode")
        val responseCode: String? = null,

        @SerializedName("paymentInstrument")
        val paymentInstrument: PaymentInstrument? = null
    )

    data class PaymentInstrument(
        @SerializedName("type")
        val type: String? = null,

        @SerializedName("vpa")
        val vpa: Any? = null, // Use `Any?` for a type that can be any object

        @SerializedName("maskedAccountNumber")
        val maskedAccountNumber: String? = null,

        @SerializedName("ifsc")
        val ifsc: String? = null,

        @SerializedName("utr")
        val utr: String? = null,

        @SerializedName("upiTransactionId")
        val upiTransactionId: String? = null,

        @SerializedName("accountHolderName")
        val accountHolderName: String? = null
    )
}

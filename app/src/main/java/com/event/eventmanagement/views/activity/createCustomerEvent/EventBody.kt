package com.event.eventmanagement.views.activity.createCustomerEvent

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventBodyRequest(
    @SerializedName("vendor_id") var vendorId: String? = null,
    @SerializedName("customer_name") var customerName: String? = null,
    @SerializedName("dob") var dob: String? = null,
    @SerializedName("anniversary_date") var anniversaryDate: String? = null,
    @SerializedName("mob_no") var mobNo: String? = null,
    @SerializedName("alt_mob_no") var altMobNo: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("country_id") var countryId: String? = null,
    @SerializedName("state_id") var stateId: String? = null,
    @SerializedName("city_id") var cityId: String? = null,
    @SerializedName("pincode") var pincode: String? = null,
    @SerializedName("event_pkg_id") var eventPkgId: Int? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("amount") var amount: Int? = null,
    @SerializedName("discount") var discount: Int? = null,
    @SerializedName("final_amount") var finalAmount: Int? = null,
    @SerializedName("advance_amount") var advanceAmount: Int? = null,
    @SerializedName("remaining_amount") var remainingAmount: Int? = null,
    @SerializedName("event_address") var eventAddress: String? = null,
    @SerializedName("event_dates") var eventDates: ArrayList<EventDates> = arrayListOf()
) : Parcelable

@Parcelize
data class EventDates(
    @SerializedName("from_date") var fromDate: String? = null,
    @SerializedName("to_date") var toDate: String? = null,
    @SerializedName("from_time") var fromTime: String? = null,
    @SerializedName("to_time") var toTime: String? = null,
    @SerializedName("remark") var remark: String? = null

) : Parcelable

data class NewEventResponse(
    @SerializedName("msg") var msg: String? = null,
    @SerializedName("data") var data: AddEventData? = AddEventData()

) {
    data class AddEventData(
        @SerializedName("eventdata") var eventdata: EventData? = EventData(),
    ) {

        data class EventData(
            @SerializedName("id") var id: Int? = null,
            @SerializedName("vendor_id") var vendorId: String? = null,
            @SerializedName("event_pkg_id") var eventPkgId: Int? = null,
            @SerializedName("customer_id") var customerId: Int? = null,
            @SerializedName("description") var description: String? = null,
            @SerializedName("amount") var amount: Int? = null,
            @SerializedName("discount") var discount: Int? = null,
            @SerializedName("final_amount") var finalAmount: Int? = null,
            @SerializedName("advance_amount") var advanceAmount: Int? = null,
            @SerializedName("remaining_amount") var remainingAmount: Int? = null,
            @SerializedName("event_address") var eventAddress: String? = null,
            @SerializedName("updatedAt") var updatedAt: String? = null,
            @SerializedName("createdAt") var createdAt: String? = null

        )
    }
}



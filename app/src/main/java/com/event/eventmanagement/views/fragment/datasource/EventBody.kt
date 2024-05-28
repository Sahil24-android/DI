package com.event.eventmanagement.views.fragment.datasource

import com.google.gson.annotations.SerializedName

data class EventBody(
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("event_name"  ) var eventName   : String? = null,
    @SerializedName("vendor_id"   ) var vendorId    : String? = null,
)

data class EventResponse(
    @SerializedName("msg"   ) var msg   : String? = null,
    @SerializedName("Event" ) var Event : Event?  = Event()
)

data class Event (
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("event_name"  ) var eventName   : String? = null,
    @SerializedName("vendor_id"   ) var vendorId    : String? = null,
    @SerializedName("updatedAt"   ) var updatedAt   : String? = null,
    @SerializedName("createdAt"   ) var createdAt   : String? = null

)

data class AllEventsResponse(
    @SerializedName("data" ) var data : ArrayList<Event> = arrayListOf()

)
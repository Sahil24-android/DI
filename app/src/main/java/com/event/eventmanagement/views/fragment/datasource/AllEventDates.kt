package com.event.eventmanagement.views.fragment.datasource

import com.google.gson.annotations.SerializedName

data class AllEventDates(
    @SerializedName("data") var data: ArrayList<EventDates> = arrayListOf()
)


data class EventDates(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("event_manage_id") var eventManageId: Int? = null,
    @SerializedName("from_date") var fromDate: String? = null,
    @SerializedName("to_date") var toDate: String? = null,
    @SerializedName("from_time") var fromTime: String? = null,
    @SerializedName("to_time") var toTime: String? = null,
    @SerializedName("remark") var remark: String? = null,
    @SerializedName("is_active") var isActive: Int? = null,
    @SerializedName("is_delete") var isDelete: Int? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("transfer") var transfer:Int? = null

)
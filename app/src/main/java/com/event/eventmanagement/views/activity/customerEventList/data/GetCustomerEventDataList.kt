package com.event.eventmanagement.views.activity.customerEventList.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetCustomerEventDataList(
    @SerializedName("data") var data: ArrayList<EventData> = arrayListOf()
) : Parcelable


@Parcelize
data class EventData(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("vendor_id") var vendorId: Int? = null,
    @SerializedName("event_pkg_id") var eventPkgId: Int? = null,
    @SerializedName("customer_id") var customerId: Int? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("amount") var amount: Int? = null,
    @SerializedName("discount") var discount: Int? = null,
    @SerializedName("final_amount") var finalAmount: Int? = null,
    @SerializedName("advance_amount") var advanceAmount: Int? = null,
    @SerializedName("remaining_amount") var remainingAmount: Int? = null,
    @SerializedName("event_address") var eventAddress: String? = null,
    @SerializedName("is_active") var isActive: Int? = null,
    @SerializedName("is_delete") var isDelete: Int? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("customerdata") var customerdata: ArrayList<CustomerData> = arrayListOf(),
    @SerializedName("event_dates") var eventDates: ArrayList<CustomerEventDates> = arrayListOf(),
    @SerializedName("event_pkg") var eventPkg: ArrayList<EventPkg> = arrayListOf(),
    @SerializedName("event_payment") var eventPayment: ArrayList<EventPayment> = arrayListOf(),
    @SerializedName("exposed_from") var exposedFrom: ExposedFrom? = ExposedFrom(),
    @SerializedName("exposed_to") var exposedTo: ExposedTo? = ExposedTo(),
    @SerializedName("transfer_event") var transferEvent: TransferEvent? = TransferEvent()
) : Parcelable

@Parcelize
data class EventPkg(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("event_id") var eventId: String? = null,
    @SerializedName("package_name") var packageName: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("amount") var amount: Int? = null,
    @SerializedName("is_active") var isActive: Int? = null,
    @SerializedName("is_delete") var isDelete: Int? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null

) : Parcelable

@Parcelize
data class CustomerData(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("vendor_id") var vendorId: Int? = null,
    @SerializedName("customer_name") var customerName: String? = null,
    @SerializedName("dob") var dob: String? = null,
    @SerializedName("anniversary_date") var anniversaryDate: String? = null,
    @SerializedName("mob_no") var mobNo: String? = null,
    @SerializedName("alt_mob_no") var altMobNo: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("country_id") var countryId: String? = null,
    @SerializedName("pincode") var pincode: Int? = null,
    @SerializedName("state_id") var stateId: String? = null,
    @SerializedName("city_id") var cityId: String? = null,
    @SerializedName("is_active") var isActive: Int? = null,
    @SerializedName("is_delete") var isDelete: Int? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null

) : Parcelable

@Parcelize
data class CustomerEventDates(
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
    @SerializedName("updatedAt") var updatedAt: String? = null

) : Parcelable


@Parcelize
data class EventPayment(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("event_manage_id") var eventManageId: Int? = null,
    @SerializedName("amount") var amount: Int? = null,
    @SerializedName("discount") var discount: Int? = null,
    @SerializedName("final_amount") var finalAmount: Int? = null,
    @SerializedName("advance_amount") var advanceAmount: Int? = null,
    @SerializedName("paid_amount") var paidAmount: Int? = null,
    @SerializedName("paid_date") var paidDate: String? = null,
    @SerializedName("remaining_amount") var remainingAmount: Int? = null,
    @SerializedName("pdf_name") var pdfName: String? = null,
    @SerializedName("pdf_url") var pdfUrl: String? = null,
    @SerializedName("is_active") var isActive: Int? = null,
    @SerializedName("is_delete") var isDelete: Int? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null
) : Parcelable


@Parcelize
data class ExposedTo(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("service_id") var serviceId: Int? = null,
    @SerializedName("service_pkg_id") var servicePkgId: Int? = null,
    @SerializedName("company_name") var companyName: String? = null,
    @SerializedName("owner_name") var ownerName: String? = null,
    @SerializedName("logo_image") var logoImage: String? = null,
    @SerializedName("mob_no") var mobNo: String? = null,
    @SerializedName("alt_mob_no") var altMobNo: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("pincode") var pincode: Int? = null,
    @SerializedName("location_id") var locationId: Int? = null,
    @SerializedName("country_id") var countryId: String? = null,
    @SerializedName("state_id") var stateId: String? = null,
    @SerializedName("city_id") var cityId: String? = null,
    @SerializedName("is_active") var isActive: Int? = null,
    @SerializedName("is_delete") var isDelete: Int? = null,
    @SerializedName("expiry_date") var expiryDate: String? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null

) : Parcelable


@Parcelize
data class ExposedFrom(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("service_id") var serviceId: Int? = null,
    @SerializedName("service_pkg_id") var servicePkgId: Int? = null,
    @SerializedName("company_name") var companyName: String? = null,
    @SerializedName("owner_name") var ownerName: String? = null,
    @SerializedName("logo_image") var logoImage: String? = null,
    @SerializedName("mob_no") var mobNo: String? = null,
    @SerializedName("alt_mob_no") var altMobNo: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("pincode") var pincode: Int? = null,
    @SerializedName("location_id") var locationId: Int? = null,
    @SerializedName("country_id") var countryId: String? = null,
    @SerializedName("state_id") var stateId: String? = null,
    @SerializedName("city_id") var cityId: String? = null,
    @SerializedName("is_active") var isActive: Int? = null,
    @SerializedName("is_delete") var isDelete: Int? = null,
    @SerializedName("expiry_date") var expiryDate: String? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null
) : Parcelable


@Parcelize
data class TransferEvent(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("event_manage_id") var eventManageId: Int? = null,
    @SerializedName("vendor_from") var vendorFrom: Int? = null,
    @SerializedName("vendor_to") var vendorTo: Int? = null,
    @SerializedName("original_event_amount") var originalEventAmount: Int? = null,
    @SerializedName("exposing_amount") var exposingAmount: Int? = null,
    @SerializedName("is_active") var isActive: Int? = null,
    @SerializedName("is_delete") var isDelete: Int? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null
) : Parcelable
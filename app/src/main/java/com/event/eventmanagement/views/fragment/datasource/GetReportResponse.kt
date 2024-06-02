package com.event.eventmanagement.views.fragment.datasource

import com.event.eventmanagement.views.activity.customerEventList.data.CustomerData
import com.event.eventmanagement.views.auth.datasource.Vendor
import com.google.gson.annotations.SerializedName

data class GetReportResponse(
    @SerializedName("data") var data: Data? = Data()

)


data class Data(
    @SerializedName("sum_expense") var sumExpense: Int? = null,
    @SerializedName("sum_pay") var sumPay: Int? = null,
    @SerializedName("get_pay") var getPay: ArrayList<GetPay> = arrayListOf(),
    @SerializedName("get_expense") var getExpense: ArrayList<GetExpense> = arrayListOf()
)


data class GetExpense(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("vendor_id") var vendorId: Int? = null,
    @SerializedName("expense_name") var expenseName: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("expense_to_whom") var expenseToWhom: String? = null,
    @SerializedName("expense_to_vendor") var expenseToVendor: Int? = null,
    @SerializedName("event_manage_id") var eventManageId: Int? = null,
    @SerializedName("employee_id") var employeeId: Int? = null,
    @SerializedName("remaining_amount") var remainingAmount: Int? = null,
    @SerializedName("amount") var amount: Int? = null,
    @SerializedName("is_active") var isActive: Int? = null,
    @SerializedName("is_delete") var isDelete: Int? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("vendor") var vendor: Vendor? = Vendor(),
    @SerializedName("employee") var employee: Employee? = Employee()
)

data class GetPay(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("event_manage_id") var eventManageId: Int? = null,
    @SerializedName("vendor_id") var vendorId: Int? = null,
    @SerializedName("amount") var amount: Int? = null,
    @SerializedName("discount") var discount: Int? = null,
    @SerializedName("final_amount") var finalAmount: Int? = null,
    @SerializedName("advance_amount") var advanceAmount: Int? = null,
    @SerializedName("paid_amount") var paidAmount: Int? = null,
    @SerializedName("paid_date") var paidDate: String? = null,
    @SerializedName("remaining_amount") var remainingAmount: Int? = null,
    @SerializedName("pdf_url") var pdfUrl: String? = null,
    @SerializedName("pdf_name") var pdfName: String? = null,
    @SerializedName("is_active") var isActive: Int? = null,
    @SerializedName("is_delete") var isDelete: Int? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("customerdata") var customerdata: CustomerData? = CustomerData()
)


data class Employee(
    @SerializedName("nmae") var name: String? = null

)
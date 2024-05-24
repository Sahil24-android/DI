package com.event.eventmanagement.views.auth.datasource

data class RegistrationDataSend(
    val companyName: String,
    val ownerName:String,
    val mobileNumber:String,
    val alternativeMobileNumber:String,
    val address:String,
    val pinCodeData: String,
    val city: String,
    val state: String,
    val country: String,
    val serviceId:String,
    val servicePackageId:String,
    val password:String
)

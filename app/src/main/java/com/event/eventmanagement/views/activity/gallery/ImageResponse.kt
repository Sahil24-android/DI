package com.event.eventmanagement.views.activity.gallery

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("msg"    ) var msg    : String? = null,
    @SerializedName("vendor" ) var vendor : Vendor? = Vendor()
)


data class Vendor (

    @SerializedName("event_id"    ) var eventId    : String? = null,
    @SerializedName("vendor_id"   ) var vendorId   : String? = null,
    @SerializedName("image_one"   ) var imageOne   : String? = null,
    @SerializedName("image_two"   ) var imageTwo   : String? = null,
    @SerializedName("image_three" ) var imageThree : String? = null,
    @SerializedName("image_four"  ) var imageFour  : String? = null,
    @SerializedName("image_five"  ) var imageFive  : String? = null,
    @SerializedName("image_six"   ) var imageSix   : String? = null,
    @SerializedName("image_seven" ) var imageSeven : String? = null,
    @SerializedName("is_active"   ) var isActive   : Int?    = null

)
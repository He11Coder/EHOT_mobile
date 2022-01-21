package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class GetQRData(

    @SerializedName("message")
    var message: String,

    @SerializedName("status")
    var status: Boolean,

    @SerializedName("token")
    var token: String?
)

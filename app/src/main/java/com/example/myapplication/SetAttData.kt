package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class SetAttData(

    @SerializedName("status")
    var status: Boolean,

    @SerializedName("message")
    var message: String
)

package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class RegData(

    @SerializedName("account")
    var account: RegAccount?,

    @SerializedName("status")
    var status: Boolean,

    @SerializedName("message")
    var message: String
)

data class RegAccount(

    @SerializedName("email")
    var email: String,

    @SerializedName("role")
    var role: String
)

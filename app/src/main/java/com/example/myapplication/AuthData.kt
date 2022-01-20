package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class AuthData(

    @SerializedName("account")
    var account: Account?,

    @SerializedName("message")
    var message: String,

    @SerializedName("status")
    var status: String
)

data class Account(
    @SerializedName("email")
    var email: String,

    @SerializedName("Token")
    var token: String,

    @SerializedName("role")
    var role: String
)
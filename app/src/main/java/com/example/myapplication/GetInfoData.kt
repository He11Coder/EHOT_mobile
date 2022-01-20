package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class GetInfoData(

    @SerializedName("data")
    var data: Data?,

    @SerializedName("message")
    var message: String,

    @SerializedName("status")
    var status: String
)

data class Data(

    @SerializedName("first_name")
    var name: String,

    @SerializedName("group")
    var group: String?,

    @SerializedName("year")
    var year: Int?
)


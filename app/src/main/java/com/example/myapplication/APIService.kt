package com.example.myapplication

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService
{
    @POST("/api/user/login")
    suspend fun logIn(@Body requestBody: RequestBody): Response<ResponseBody>
}
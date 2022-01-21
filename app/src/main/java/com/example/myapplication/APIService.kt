package com.example.myapplication

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface APIService
{
    @POST("/api/user/login")
    suspend fun logIn(@Body requestBody: RequestBody): Response<AuthData>

    @POST("/api/teacher/qrcode")
    suspend fun setAtt(@Body requestBody: RequestBody, @Header("Authorization") auth: String): Response<SetAttData>

    @GET("/api/user/getInfo")
    suspend fun getInfo(@Header("Authorization") auth: String): Response<GetInfoData>

    @GET("/api/student/qrcode")
    suspend fun getQR(@Header("Authorization") auth: String): Response<GetQRData>
}
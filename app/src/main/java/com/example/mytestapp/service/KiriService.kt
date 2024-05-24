package com.example.mytestapp.service

import com.example.mytestapp.model.request.*
import com.example.mytestapp.model.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SignService {

    @POST("signup/")
    fun signup(
        @Body request: signuprequest
    ): Call<signupresponse>

    @POST("login/")
    fun login(
        @Body request: loginrequest
    ): Call<loginresponse>
}

<<<<<<< HEAD
interface ProfileService{
    @POST("profile/")
    fun profile(
        @Body request: profilerequest
    ): Call<profileresponse>
}

interface RoommateService{
    @POST("roommate/")
    fun roommate(
        @Body request: roommaterequest
    ): Call<roommateresponse>
}

// 채팅
//interface ChatService {
//
//    @GET("messages")
//    fun getMessages(): Call<List<ChatMessage>>
//
//    @POST("messages")
//    fun postMessage(@Body message: ChatMessage): Call<ChatMessage>
//
//    @POST("block")
//    fun blockUser(@Body blockData: BlockData): Call<Void>
//
//    @POST("report")
//    fun reportUser(@Body reportData: ReportData): Call<Void>
//}
=======
interface ChatService {
    @GET("messages/")
    fun getMessages(): Call<List<ChatMessage>>

    @POST("messages/")
    fun postMessage(@Body message: ChatMessage): Call<ChatMessage>

    @POST("block/")
    fun blockUser(@Body blockData: BlockData): Call<Void>

    @POST("report/")
    fun reportUser(@Body reportData: ReportData): Call<Void>
}
>>>>>>> 45e3a0353b78ff1d9d732d7c52b58082c5842799
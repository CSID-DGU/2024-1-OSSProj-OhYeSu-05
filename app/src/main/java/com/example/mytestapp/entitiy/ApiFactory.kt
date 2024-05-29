package com.example.mytestapp.entitiy


//import com.example.mytestapp.BuildConfig  // 일단 주석 처리
import com.example.mytestapp.service.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object ApiFactory {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://638c-58-140-213-197.ngrok-free.app/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()

    }

    inline fun <reified T> create(): T = retrofit.create<T>(T::class.java)
}

object KiriServicePool {
    val signupService = ApiFactory.create<SignService>()
    val loginService = ApiFactory.create<SignService>()
    val ProfileService = ApiFactory.create<ProfileService>()
    val RoommateService = ApiFactory.create<RoommateService>()
    val matchingService = ApiFactory.create<MatchingService>()
    val chatService = ApiFactory.create<ChatService>()

//    val createroomService = ApiFactory.create<CreateRoomService>()
//    val searchroomService = ApiFactory.create<SearchRoomService>()

//    val roomlistService = ApiFactory.create<RoomlistService>()
//    val enterroomService = ApiFactory.create<EnterRoomlistService>()
}



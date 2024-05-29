package com.example.mytestapp.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class profilerequest(
        @SerialName("UserID")
        val UserID: String,

        //option1 - mbti
        @SerialName("m")
        val m: Int,
        @SerialName("b")
        val b: Int,
        @SerialName("t")
        val t: Int,
        @SerialName("i")
        val i: Int,

        // option2
        @SerialName("grade")    // 얘는 이진 아님 (학년)
        val grade: Int,
        @SerialName("smoking")
        val smoking: Int,
        @SerialName("first_lesson")
        val first_lesson: Int,
        @SerialName("sleeping_habit")
        val sleeping_habit: Int,
        @SerialName("sharing_daily_needs")
        val sharing_daily_needs: Int,

        //option3
        @SerialName("internal_communication")
        val internal_communication: Int,
        @SerialName("heat_sensitive")
        val heat_sensitive: Int,
        @SerialName("cold_sensitive")
        val cold_sensitive: Int,
        @SerialName("drinking_frequency")
        val drinking_frequency: Int,

        //option4
        @SerialName("cleanliness")
        val cleanliness: Int,
        @SerialName("noise_sensitivity")
        val noise_sensitivity: Int,
        @SerialName("sleep_schedule")
        val sleep_schedule: Int,
        @SerialName("up_schedule")
        val up_schedule: Int
)

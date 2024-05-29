package com.example.mytestapp.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class roommaterequest(

    //option1 - mbti
    @SerialName("Y_m")
    val Y_m: Int,
    @SerialName("Y_grade")    // 얘는 이진 아님 (학년)
    val Y_grade: Int,
    @SerialName("Y_smoking")
    val Y_smoking: Int,
    @SerialName("Y_first_lesson")
    val Y_first_lesson: Int,
    @SerialName("Y_sleeping_habit")
    val Y_sleeping_habit: Int,

    // option2
    @SerialName("Y_sharing_daily_needs")
    val Y_sharing_daily_needs: Int,
    @SerialName("Y_internal_communication")
    val Y_internal_communication: Int,
    @SerialName("Y_heat_sensitive")
    val Y_heat_sensitive: Int,
    @SerialName("Y_cold_sensitive")
    val Y_cold_sensitive: Int,

    //option3
    @SerialName("Y_drinking_frequency")
    val Y_drinking_frequency: Int,
    @SerialName("Y_cleanliness")
    val Y_cleanliness: Int,
    @SerialName("Y_noise_sensitivity")
    val Y_noise_sensitivity: Int,
    @SerialName("Y_sleep_schedule")
    val Y_sleep_schedule: Int,

    //option4
    @SerialName("Y_up_schedule")
    val Y_up_schedule: Int
)

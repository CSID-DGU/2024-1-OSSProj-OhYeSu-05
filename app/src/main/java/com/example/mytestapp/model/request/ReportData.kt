package com.example.mytestapp.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ReportData(
    @SerialName("ReportID")
    val ReportID: String,
    @SerialName("UserID")
    val UserID: String,
    @SerialName("Reason")
    val Reason: String,
    @SerialName("ReporterID")
    val ReporterID: String,
    @SerialName("ReportedID")
    val ReportedID: String,
    @SerialName("Timestamp")
    val timestamp: String = System.currentTimeMillis().toString()  // 기본값으로 현재 시간을 사용
)
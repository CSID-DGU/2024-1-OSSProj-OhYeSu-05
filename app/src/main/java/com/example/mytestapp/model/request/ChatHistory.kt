package com.example.mytestapp.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatHistory(
    @SerialName("HistoryID")
    val historyID: String,
    @SerialName("UserID")
    val userID: String,
    @SerialName("UserID2")
    val userID2: String,
    @SerialName("UserID2name") // 백엔드에서 처리된 사용자 이름
    val userID2name: String,
    @SerialName("recentMessage")
    val recentMessage: String,
    @SerialName("AccessedTime")
    val accessedTime: String = System.currentTimeMillis().toString()
)

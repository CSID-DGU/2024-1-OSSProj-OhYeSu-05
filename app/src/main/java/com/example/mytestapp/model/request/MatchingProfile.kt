package com.example.mytestapp.model.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchingProfile(
    @SerialName("MatchID")
    val matchID: String,
    @SerialName("UserID")
    val userID: String,
    @SerialName("MatchScore")
    val matchScore: String,
    @SerialName("CreatedAt")
    val createdAt: String,
    @SerialName("UpdateAt")
    val updateAt: String,
    @SerialName("User1ID")
    val user1ID: String,
    @SerialName("User2ID")
    val user2ID: String,
    @SerialName("User3ID")
    val user3ID: String,
    @SerialName("User4ID")
    val user4ID: String,
    @SerialName("User5ID")
    val user5ID: String,
    @SerialName("User1Name")
    val user1Name: String,
    @SerialName("User2Name")
    val user2Name: String,
    @SerialName("User3Name")
    val user3Name: String,
    @SerialName("User4Name")
    val user4Name: String,
    @SerialName("User5Name")
    val user5Name: String
)










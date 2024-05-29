package com.example.mytestapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytestapp.model.request.ChatHistory
import com.example.mytestapp.service.KiriService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatHistoryViewModel : ViewModel() {
    private val _chatHistories = MutableLiveData<List<ChatHistory>>()
    val chatHistories: LiveData<List<ChatHistory>> get() = _chatHistories

    init {
        _chatHistories.value = mutableListOf()
    }

    fun loadChatHistories(currentUserId: String) {
        KiriService.chatService.getChatHistory(currentUserId).enqueue(object : Callback<List<ChatHistory>> {
            override fun onResponse(call: Call<List<ChatHistory>>, response: Response<List<ChatHistory>>) {
                if (response.isSuccessful) {
                    _chatHistories.value = response.body() ?: emptyList()
                } else {
                    // Handle the error
                }
            }

            override fun onFailure(call: Call<List<ChatHistory>>, t: Throwable) {
                // Handle the failure
            }
        })
    }
}

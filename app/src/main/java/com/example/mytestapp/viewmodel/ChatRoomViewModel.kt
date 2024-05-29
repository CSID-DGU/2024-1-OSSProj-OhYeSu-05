package com.example.mytestapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytestapp.model.request.ChatMessage
import com.example.mytestapp.entitiy.KiriServicePool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatRoomViewModel : ViewModel() {
    private val _chatMessages = MutableLiveData<List<ChatMessage>>()
    val chatMessages: LiveData<List<ChatMessage>> get() = _chatMessages

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        _chatMessages.value = mutableListOf()
        _errorMessage.value = ""  // 초기값을 빈 문자열로 설정
    }

    fun addMessage(message: ChatMessage) {
        _chatMessages.value = _chatMessages.value?.plus(message)
    }

    fun setMessages(messages: List<ChatMessage>) {
        _chatMessages.value = messages
    }

    fun loadMessages(currentUserId: String, targetUserId: String) {
        KiriServicePool.chatService.getMessages(currentUserId, targetUserId).enqueue(object : Callback<List<ChatMessage>> {
            override fun onResponse(call: Call<List<ChatMessage>>, response: Response<List<ChatMessage>>) {
                if (response.isSuccessful) {
                    _chatMessages.value = response.body()
                    _errorMessage.value = ""  // 성공 시 에러 메시지를 빈 문자열로 설정
                } else {
                    _chatMessages.value = emptyList()
                    _errorMessage.value = "Failed to load messages: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<ChatMessage>>, t: Throwable) {
                _chatMessages.value = emptyList()
                _errorMessage.value = "Failed to load messages: ${t.message}"
            }
        })
    }
}

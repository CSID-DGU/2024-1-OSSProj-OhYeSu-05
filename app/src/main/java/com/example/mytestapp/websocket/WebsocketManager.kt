package com.example.mytestapp.websocket

import android.os.Handler
import android.os.Looper
import com.example.mytestapp.model.request.ChatMessage
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject

class WebSocketManager(
    private val url: String,
    private val onMessageReceived: (ChatMessage) -> Unit,
    private val onConnectionFailed: (String) -> Unit
) {
    private lateinit var webSocket: WebSocket
    private val client = OkHttpClient()
    private val handler = Handler(Looper.getMainLooper())
    private var isConnected = false

    fun connect() {
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                isConnected = true
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                handler.post {
                    val jsonObject = JSONObject(text)
                    val chatMessage = ChatMessage(
                        messageId = jsonObject.getString("messageId"),
                        receiverId = jsonObject.getString("receiverId"),
                        senderId = jsonObject.getString("senderId"),
                        content = jsonObject.getString("content"),
                        timestamp = jsonObject.getString("timestamp"),
                        formattedTimestamp = jsonObject.getString("formattedTimestamp"),
                        senderName = jsonObject.getString("senderName")
                    )
                    onMessageReceived(chatMessage)
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
                isConnected = false
                handler.post {
                    onConnectionFailed(t.message ?: "Unknown error")
                }
                reconnect()
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                isConnected = false
            }
        })
    }

    private fun reconnect() {
        handler.postDelayed({
            if (!isConnected) {
                connect()
            }
        }, 5000)
    }

    fun sendMessage(message: String) {
        if (isConnected) {
            webSocket.send(message)
        }
    }

    fun disconnect() {
        if (isConnected) {
            webSocket.close(1000, "Goodbye")
        }
    }
}

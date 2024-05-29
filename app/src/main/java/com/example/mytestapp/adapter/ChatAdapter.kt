package com.example.mytestapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestapp.databinding.ChatMessageItemBinding
import com.example.mytestapp.model.request.ChatMessage

class ChatAdapter(
    private val currentUserId: String,
    private var messages: List<ChatMessage>
) : RecyclerView.Adapter<ChatAdapter.ChatMessageViewHolder>() {

    fun submitList(newMessages: List<ChatMessage>) {
        messages = newMessages
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageViewHolder {
        val binding = ChatMessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatMessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatMessageViewHolder, position: Int) {
        holder.bind(messages[position], currentUserId)
    }

    override fun getItemCount(): Int = messages.size

    class ChatMessageViewHolder(private val binding: ChatMessageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatMessage: ChatMessage, currentUserId: String) {
            binding.chatMessage = chatMessage

            if (chatMessage.senderId == currentUserId) {
                // 발신 메시지일 경우
                binding.layoutSentMessage.visibility = View.VISIBLE
                binding.layoutReceivedMessage.visibility = View.GONE
            } else {
                // 수신 메시지일 경우
                binding.layoutSentMessage.visibility = View.GONE
                binding.layoutReceivedMessage.visibility = View.VISIBLE
            }

            binding.executePendingBindings()
        }
    }
}

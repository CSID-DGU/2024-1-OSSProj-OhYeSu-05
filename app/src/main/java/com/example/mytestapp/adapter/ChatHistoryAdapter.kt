package com.example.mytestapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestapp.databinding.ItemChatHistoryBinding
import com.example.mytestapp.model.request.ChatHistory
import com.example.mytestapp.viewmodel.ChatHistoryViewModel

class ChatHistoryAdapter(
    private val viewModel: ChatHistoryViewModel,
    private val itemClickListener: OnItemClickListener
) : ListAdapter<ChatHistory, ChatHistoryAdapter.ChatHistoryViewHolder>(ChatHistoryDiffCallback()) {

    interface OnItemClickListener {
        fun onItemClick(chatHistory: ChatHistory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHistoryViewHolder {
        val binding = ItemChatHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatHistoryViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel, itemClickListener)
    }

    class ChatHistoryViewHolder(private val binding: ItemChatHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatHistory, viewModel: ChatHistoryViewModel, clickListener: OnItemClickListener) {
            binding.history = item
            binding.viewModel = viewModel
            binding.root.setOnClickListener {
                clickListener.onItemClick(item)
            }
            binding.executePendingBindings()
        }
    }

    class ChatHistoryDiffCallback : DiffUtil.ItemCallback<ChatHistory>() {
        override fun areItemsTheSame(oldItem: ChatHistory, newItem: ChatHistory): Boolean {
            return oldItem.historyID == newItem.historyID
        }

        override fun areContentsTheSame(oldItem: ChatHistory, newItem: ChatHistory): Boolean {
            return oldItem == newItem
        }
    }
}

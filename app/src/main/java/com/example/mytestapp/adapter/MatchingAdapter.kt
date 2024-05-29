package com.example.mytestapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestapp.databinding.ItemMatchingBinding
import com.example.mytestapp.model.request.MatchingProfile
import com.example.mytestapp.viewmodel.MatchingViewModel

class MatchingAdapter(private val viewModel: MatchingViewModel) :
    ListAdapter<MatchingProfile, MatchingAdapter.MatchingViewHolder>(MatchingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchingViewHolder {
        val binding = ItemMatchingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchingViewHolder, position: Int) {
        val profile = getItem(position)
        val userName = when (position % 5) { // 각 아이템의 위치에 따라 UserID 1, 2, 3, 4, 5 중 하나를 선택
            0 -> profile.user1Name
            1 -> profile.user2Name
            2 -> profile.user3Name
            3 -> profile.user4Name
            else -> profile.user5Name
        }
        holder.bind(viewModel, profile, userName)
    }

    class MatchingViewHolder(private val binding: ItemMatchingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: MatchingViewModel, profile: MatchingProfile, userName: String) {
            binding.viewModel = viewModel
            binding.profile = profile
            binding.userName = userName
            binding.executePendingBindings()
        }
    }
}

class MatchingDiffCallback : DiffUtil.ItemCallback<MatchingProfile>() {
    override fun areItemsTheSame(oldItem: MatchingProfile, newItem: MatchingProfile): Boolean {
        return oldItem.matchID == newItem.matchID
    }

    override fun areContentsTheSame(oldItem: MatchingProfile, newItem: MatchingProfile): Boolean {
        return oldItem == newItem
    }
}

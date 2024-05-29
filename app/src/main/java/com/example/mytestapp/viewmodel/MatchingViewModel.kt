package com.example.mytestapp.viewmodel

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytestapp.R
import com.example.mytestapp.chat.ChatActivity
import com.example.mytestapp.model.request.MatchingProfile
import com.example.mytestapp.service.MatchingService
import com.example.mytestapp.entitiy.KiriServicePool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchingViewModel : ViewModel() {
    private val _matchingProfiles = MutableLiveData<List<MatchingProfile>>()
    val matchingProfiles: LiveData<List<MatchingProfile>> = _matchingProfiles

    private val matchingService: MatchingService = KiriServicePool.matchingService

    // 로그인 시 저장된 UserID를 SharedPreferences에서 불러와 데이터를 로드
    fun loadMatchingProfiles(context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("UserID", null)

        userId?.let {
            matchingService.getMatchingProfiles(it).enqueue(object : Callback<List<MatchingProfile>> {
                override fun onResponse(call: Call<List<MatchingProfile>>, response: Response<List<MatchingProfile>>) {
                    if (response.isSuccessful) {
                        _matchingProfiles.value = response.body()
                    } else {
                        _matchingProfiles.value = emptyList()
                    }
                }

                override fun onFailure(call: Call<List<MatchingProfile>>, t: Throwable) {
                    _matchingProfiles.value = emptyList()
                }
            })
        }
    }

    // 프로필을 클릭했을 때 ChatActivity로 이동하는 로직
    fun onProfileClicked(view: View, profile: MatchingProfile) {
        val context = view.context
        val intent = Intent(context, ChatActivity::class.java).apply {
            putExtra("targetUserId", profile.userID)
            putExtra("targetUserName", profile.userID) // 이름 대신 userID를 사용, 필요에 따라 수정
        }
        context.startActivity(intent)
    }

    // 메뉴 버튼을 클릭했을 때의 로직
    fun onMenuButtonClicked(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.inflate(R.menu.item_menu)
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // 홈으로 이동하는 로직
                    true
                }
                R.id.nav_roomate -> {
                    // 룸메이트 추천으로 이동하는 로직
                    true
                }
                R.id.nav_chat -> {
                    // 채팅으로 이동하는 로직
                    true
                }
                R.id.nav_mypage -> {
                    // 마이페이지로 이동하는 로직
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}

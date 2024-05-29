package com.example.mytestapp

import MyPageFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mytestapp.chat.ChatHistoryFragment
import com.example.mytestapp.databinding.ActivityMainBinding
import com.example.mytestapp.match.MatchingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var userID: String
    private lateinit var userName: String
    private lateinit var userStudentID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그인 화면에서 전달된 사용자 정보를 저장
        userID = intent.getStringExtra("userid") ?: "unknownID"
        userName = intent.getStringExtra("username") ?: "unknownName"
        userStudentID = intent.getStringExtra("studentid") ?: "unknownStudentID"

        // 기본 프래그먼트 설정
        replaceFragment(HomeFragment())

        // 하단 네비게이션 설정
        binding.bnvMain.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_roomate -> {
                    replaceFragment(MatchingFragment())
                    true
                }
                R.id.nav_chat -> {
                    replaceFragment(ChatHistoryFragment())
                    true
                }
                R.id.nav_mypage -> {
                    navigateToMyPageFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun navigateToMyPageFragment() {
        // 사용자 정보를 기반으로 Bundle 생성
        val bundle = Bundle().apply {
            putString("name", userName)
            putString("studentId", userStudentID)
        }

        // MyPageFragment 인스턴스 생성 및 전환
        val myPageFragment = MyPageFragment().apply {
            arguments = bundle
        }
        replaceFragment(myPageFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit()
    }
}

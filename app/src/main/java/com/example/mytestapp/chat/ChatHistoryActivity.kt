package com.example.mytestapp.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mytestapp.R

class ChatHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_history)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ChatHistoryFragment.newInstance())
                .commitNow()
        }
    }
}

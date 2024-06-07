package com.example.mytestapp.sign

import com.example.mytestapp.entitiy.KiriServicePool
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mytestapp.MainActivity
import com.example.mytestapp.databinding.ActivityLoginBinding
import com.example.mytestapp.model.request.loginrequest
import com.example.mytestapp.model.response.loginresponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginService = KiriServicePool.loginService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val iD = binding.UserID
        val password = binding.loginPassword

        binding.signupBtn.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {
            if (iD.text.isNotEmpty() && password.text.isNotEmpty()) {
                loginService.login(
                    loginrequest(
                        iD.text.toString(),
                        password.text.toString()
                    )
                ).enqueue(object : Callback<loginresponse> {
                    override fun onResponse(
                        call: Call<loginresponse>,
                        response: Response<loginresponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(applicationContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                            val userID = response.body()?.user_id

                            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("UserID", userID)
                            editor.apply()

                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                        }
                        else {
                            Log.d("ffffff", response.body()?.success.toString())
                            Toast.makeText(applicationContext, "아이디 혹은 비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<loginresponse>, t: Throwable) {
                        Log.d("ffffff", t.message.toString())
                        Toast.makeText(applicationContext, "서버통신 실패", Toast.LENGTH_SHORT).show()
                    }

                })
            } else {
                if (iD.text.isEmpty()) {
                    Toast.makeText(applicationContext, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
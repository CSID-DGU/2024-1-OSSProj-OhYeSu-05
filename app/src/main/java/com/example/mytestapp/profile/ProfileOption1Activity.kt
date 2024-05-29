package com.example.mytestapp.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mytestapp.MainActivity
import com.example.mytestapp.R
import com.example.mytestapp.databinding.ActivityProfileOption1Binding


class ProfileOption1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileOption1Binding // 변경된 부분

    // 변수 초기화
    private var mValue = -1
    private var bValue = -1
    private var tValue = -1
    private var iValue = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileOption1Binding.inflate(layoutInflater) // 변경된 부분
        setContentView(binding.root)

        // 외향형(E) / 내향형(I) 버튼
        binding.mE.setOnClickListener {
            mValue = 1 // 외향형 선택 시 값 설정
            binding.mE.setBackgroundResource(R.drawable.option_bar_left_selected) // 선택된 상태 배경으로 변경
            binding.mI.setBackgroundResource(R.drawable.option_bar_right) // 내향형 버튼의 배경 초기화
        }
        binding.mI.setOnClickListener {
            mValue = 0 // 내향형 선택 시 값 설정
            binding.mI.setBackgroundResource(R.drawable.option_bar_right_selected) // 선택된 상태 배경으로 변경
            binding.mE.setBackgroundResource(R.drawable.option_bar_left) // 외향형 버튼의 배경 초기화
        }

        // 감각형(S) / 직관형(N) 버튼
        binding.bS.setOnClickListener {
            bValue = 1 // 감각형 선택 시 값 설정
            binding.bS.setBackgroundResource(R.drawable.option_bar_left_selected) // 선택된 상태 배경으로 변경
            binding.bN.setBackgroundResource(R.drawable.option_bar_right) // 직관형 버튼의 배경 초기화
        }
        binding.bN.setOnClickListener {
            bValue = 0 // 직관형 선택 시 값 설정
            binding.bN.setBackgroundResource(R.drawable.option_bar_right_selected) // 선택된 상태 배경으로 변경
            binding.bS.setBackgroundResource(R.drawable.option_bar_left) // 감각형 버튼의 배경 초기화
        }

        // 감정형(F) / 사고형(T) 버튼
        binding.tT.setOnClickListener {
            tValue = 1 // 감정형 선택 시 값 설정
            binding.tT.setBackgroundResource(R.drawable.option_bar_left_selected) // 선택된 상태 배경으로 변경
            binding.tF.setBackgroundResource(R.drawable.option_bar_right) // 사고형 버튼의 배경 초기화
        }
        binding.tF.setOnClickListener {
            tValue = 0 // 사고형 선택 시 값 설정
            binding.tF.setBackgroundResource(R.drawable.option_bar_right_selected) // 선택된 상태 배경으로 변경
            binding.tT.setBackgroundResource(R.drawable.option_bar_left) // 감정형 버튼의 배경 초기화
        }

        // 판단형(J) / 인식형(P) 버튼
        binding.iJ.setOnClickListener {
            iValue = 1 // 판단형 선택 시 값 설정
            binding.iJ.setBackgroundResource(R.drawable.option_bar_left_selected) // 선택된 상태 배경으로 변경
            binding.iP.setBackgroundResource(R.drawable.option_bar_right) // 인식형 버튼의 배경 초기화
        }
        binding.iP.setOnClickListener {
            iValue = 0 // 인식형 선택 시 값 설정
            binding.iP.setBackgroundResource(R.drawable.option_bar_right_selected) // 선택된 상태 배경으로 변경
            binding.iJ.setBackgroundResource(R.drawable.option_bar_left) // 판단형 버튼의 배경 초기화
        }


        // 다음으로 버튼 클릭 시 선택된 값 전달
        binding.btnNext.setOnClickListener {
            if (isValuesSelected()) {
                val intent = Intent(this, ProfileOption2Activity::class.java)
                intent.putExtra("m", mValue)
                intent.putExtra("b", bValue)
                intent.putExtra("t", tValue)
                intent.putExtra("i", iValue)
                startActivity(intent)
                finish()
            } else {
                // 필수 정보를 선택하지 않았을 경우에 대한 처리
                Toast.makeText(this, "모든 선택지를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 이전으로 버튼 클릭 시 MainActivity 로 이동
        binding.btnPrev.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 메인으로 버튼 클릭 시 MainActivity 로 이동
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // 선택된 값이 모두 있는지 확인하는 함수
    private fun isValuesSelected(): Boolean {
        return mValue != -1 && bValue != -1 && tValue != -1 && iValue != -1
    }
}

package com.example.mytestapp.match

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mytestapp.MainActivity
import com.example.mytestapp.R

class MatchingOption4Activity : AppCompatActivity() {

    private lateinit var firstYUpscheduleRadioGroup: RadioGroup
    private lateinit var secondYUpscheduleRadioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_option_4)

        // 라디오 그룹 초기화
        firstYUpscheduleRadioGroup = findViewById(R.id.first_Y_up_schedule_radio_group)
        secondYUpscheduleRadioGroup = findViewById(R.id.second_Y_up_schedule_radio_group)

        // 버튼 초기화
        val btnBack: Button = findViewById(R.id.btn_back)
        val btnFinish: Button = findViewById(R.id.btn_finish)
        val btnPrev: Button = findViewById(R.id.btn_prev)

        // 버튼 클릭 리스너 설정
        btnBack.setOnClickListener {
            // 메인 화면으로 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnPrev.setOnClickListener {
            // 이전 화면으로 이동 (MatchingOption3Activity)
            val intent = Intent(this, MatchingOption3Activity::class.java)
            startActivity(intent)
            finish()
        }

        btnFinish.setOnClickListener {
            // 매칭 조건 입력 완성 버튼 클릭 시, 선택된 값을 가져와서 처리
            val upSchedule = getSelectedUpSchedule()

            if (upSchedule == -1) {
                // 필수 정보를 선택하지 않았을 경우에 대한 처리
                Toast.makeText(this, "모든 선택지를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                // 매칭을 시작합니다 라는 메시지를 표시
                Toast.makeText(this, "매칭을 시작합니다.", Toast.LENGTH_SHORT).show()
                // 선택한 값들을 다음 액티비티로 전달하고 해당 액티비티로 이동
                val intent = Intent(this, MatchingING::class.java)   // 추천 목록 구현 필요
                // 여기에 선택한 값들을 intent에 추가하는 코드 추가해야 함
                startActivity(intent)
                finish()
            }
        }
    }

    private fun getSelectedUpSchedule(): Int {
        val firstGroupCheckedId = firstYUpscheduleRadioGroup.checkedRadioButtonId
        val secondGroupCheckedId = secondYUpscheduleRadioGroup.checkedRadioButtonId

        return when {
            firstGroupCheckedId != -1 -> { // 첫 번째 라디오 그룹에서 선택된 경우
                when (firstGroupCheckedId) {
                    R.id.Y_up_early -> 0
                    R.id.Y_up_seven -> 1
                    R.id.Y_up_normal -> 2
                    else -> -1 // 선택된 값이 없는 경우 -1을 반환
                }
            }
            secondGroupCheckedId != -1 -> { // 두 번째 라디오 그룹에서 선택된 경우
                when (secondGroupCheckedId) {
                    R.id.Y_up_late -> 3
                    R.id.Y_up_very_late -> 4
                    else -> -1 // 선택된 값이 없는 경우 -1을 반환
                }
            }
            else -> -1 // 선택된 값이 없는 경우 -1을 반환
        }
    }
}

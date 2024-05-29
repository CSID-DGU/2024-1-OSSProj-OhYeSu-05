package com.example.mytestapp.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mytestapp.MainActivity
import com.example.mytestapp.R

class ProfileOption4Activity : AppCompatActivity() {

    private lateinit var cleanlinessRadioGroup: RadioGroup
    private lateinit var noiseSensitivityRadioGroup: RadioGroup
    private lateinit var firstSleepScheduleRadioGroup: RadioGroup
    private lateinit var secondSleepScheduleRadioGroup: RadioGroup
    private lateinit var firstUpScheduleRadioGroup: RadioGroup
    private lateinit var secondUpScheduleRadioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_option_4)

        // 라디오 그룹 초기화
        cleanlinessRadioGroup = findViewById(R.id.cleanliness_radio_group)
        noiseSensitivityRadioGroup = findViewById(R.id.noise_sensitivity_radio_group)
        firstSleepScheduleRadioGroup = findViewById(R.id.first_sleep_schedule_radio_group)
        secondSleepScheduleRadioGroup = findViewById(R.id.second_sleep_schedule_radio_group)
        firstUpScheduleRadioGroup = findViewById(R.id.first_up_schedule_radio_group)
        secondUpScheduleRadioGroup = findViewById(R.id.second_up_schedule_radio_group)

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
            // 이전 화면으로 이동 (ProfileOption3Activity)
            val intent = Intent(this, ProfileOption3Activity::class.java)
            startActivity(intent)
            finish()
        }

        btnFinish.setOnClickListener {
            // 프로필 완성 버튼 클릭 시, 선택된 값을 가져와서 처리
            val cleanliness = getSelectedCleanliness()
            val noiseSensitivity = getSelectedNoiseSensitivity()
            val sleepSchedule = getSelectedSleepSchedule()
            val upSchedule = getSelectedUpSchedule()

            if (cleanliness == -1 ||
                noiseSensitivity == -1 ||
                sleepSchedule == -1 ||
                upSchedule == -1) {
                // 필수 정보를 선택하지 않았을 경우에 대한 처리
                Toast.makeText(this, "모든 선택지를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                // 프로필 생성이 완료되었음을 알리는 토스트 메시지 출력
                Toast.makeText(this, "프로필 생성이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 청결에 대한 선택된 값을 반환하는 함수
    private fun getSelectedCleanliness(): Int {
        return when (cleanlinessRadioGroup.checkedRadioButtonId) {
            R.id.cleanliness_sensitive -> 0
            R.id.cleanliness_normal -> 1
            R.id.cleanliness_not_sensitive -> 2
            else -> -1 // 선택된 값이 없는 경우 -1을 반환
        }
    }

    // 소음 민감도에 대한 선택된 값을 반환하는 함수
    private fun getSelectedNoiseSensitivity(): Int {
        return when (noiseSensitivityRadioGroup.checkedRadioButtonId) {
            R.id.noise_sensitive -> 0
            R.id.noise_normal -> 1
            R.id.noise_not_sensitive -> 2
            else -> -1 // 선택된 값이 없는 경우 -1을 반환
        }
    }

    // 취침 시간에 대한 선택된 값을 반환하는 함수
    private fun getSelectedSleepSchedule(): Int {
        val firstGroupCheckedId = firstSleepScheduleRadioGroup.checkedRadioButtonId
        val secondGroupCheckedId = secondSleepScheduleRadioGroup.checkedRadioButtonId

        return when {
            firstGroupCheckedId != -1 -> { // 첫 번째 라디오 그룹에서 선택된 경우
                when (firstGroupCheckedId) {
                    R.id.sleep_early -> 0
                    R.id.sleep_ten -> 1
                    R.id.sleep_normal -> 2
                    else -> -1 // 선택된 값이 없는 경우 -1을 반환
                }
            }
            secondGroupCheckedId != -1 -> { // 두 번째 라디오 그룹에서 선택된 경우
                when (secondGroupCheckedId) {
                    R.id.sleep_late -> 3
                    R.id.sleep_very_late -> 4
                    else -> -1 // 선택된 값이 없는 경우 -1을 반환
                }
            }
            else -> -1 // 선택된 값이 없는 경우 -1을 반환
        }
    }

    // 기상 시간에 대한 선택된 값을 반환하는 함수
    private fun getSelectedUpSchedule(): Int {
        val firstGroupCheckedId = firstUpScheduleRadioGroup.checkedRadioButtonId
        val secondGroupCheckedId = secondUpScheduleRadioGroup.checkedRadioButtonId

        return when {
            firstGroupCheckedId != -1 -> { // 첫 번째 라디오 그룹에서 선택된 경우
                when (firstGroupCheckedId) {
                    R.id.up_early -> 0
                    R.id.up_seven -> 1
                    R.id.up_normal -> 2
                    else -> -1 // 선택된 값이 없는 경우 -1을 반환
                }
            }
            secondGroupCheckedId != -1 -> { // 두 번째 라디오 그룹에서 선택된 경우
                when (secondGroupCheckedId) {
                    R.id.up_late -> 3
                    R.id.up_very_late -> 4
                    else -> -1 // 선택된 값이 없는 경우 -1을 반환
                }
            }
            else -> -1 // 선택된 값이 없는 경우 -1을 반환
        }
    }
}

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

class MatchingOption3Activity : AppCompatActivity() {

    private lateinit var YdrinkingFrequencyRadioGroup: RadioGroup
    private lateinit var YcleanlinessRadioGroup: RadioGroup
    private lateinit var YnoiseSensitivityRadioGroup: RadioGroup
    private lateinit var firstYSleepScheduleRadioGroup: RadioGroup
    private lateinit var secondYSleepScheduleRadioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_option_3)

        // 라디오 그룹 초기화
        YdrinkingFrequencyRadioGroup = findViewById(R.id.Y_drinking_frequency_radio_group)
        YcleanlinessRadioGroup = findViewById(R.id.Y_cleanliness_radio_group)
        YnoiseSensitivityRadioGroup = findViewById(R.id.Y_noise_sensitivity_radio_group)
        firstYSleepScheduleRadioGroup = findViewById(R.id.first_Y_sleep_schedule_radio_group)
        secondYSleepScheduleRadioGroup = findViewById(R.id.second_Y_sleep_schedule_radio_group)


        // 버튼 초기화
        val btnBack: Button = findViewById(R.id.btn_back)
        val btnNext: Button = findViewById(R.id.btn_next)
        val btnPrev: Button = findViewById(R.id.btn_prev)

        // 버튼 클릭 리스너 설정
        btnBack.setOnClickListener {
            // 메인 화면으로 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnPrev.setOnClickListener {
            // 이전 화면으로 이동 (MatchingOption2Activity)
            val intent = Intent(this, MatchingOption2Activity::class.java)
            startActivity(intent)
            finish()
        }

        btnNext.setOnClickListener {
            // 다음 버튼 클릭 시 선택한 값을 확인하고 처리
            val cleanliness = getSelectedCleanliness()
            val noiseSensitivity = getSelectedNoiseSensitivity()
            val sleepSchedule = getSelectedSleepSchedule()
            val drinkingFrequency = getSelectedDrinkingFrequency()

            if (cleanliness == -1 ||
                noiseSensitivity == -1 ||
                sleepSchedule == -1 ||
                drinkingFrequency == -1) {
                // 필수 정보를 선택하지 않았을 경우에 대한 처리
                Toast.makeText(this, "모든 선택지를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                // 선택한 값들을 다음 액티비티로 전달하고 해당 액티비티로 이동
                val intent = Intent(this, MatchingOption4Activity::class.java)
                // 여기에 선택한 값들을 intent에 추가하는 코드 추가해야 함
                startActivity(intent)
                finish()
            }
        }
    }

    // 음주 빈도에 대한 선택된 값을 반환하는 함수
    private fun getSelectedDrinkingFrequency(): Int {
        return when (YdrinkingFrequencyRadioGroup.checkedRadioButtonId) {
            R.id.Y_frequency_zero -> 0
            R.id.Y_frequency_third -> 1
            R.id.Y_frequency_fifth -> 2
            else -> -1 // 선택된 값이 없는 경우 -1을 반환
        }
    }

    // 청결에 대한 선택된 값을 반환하는 함수
    private fun getSelectedCleanliness(): Int {
        return when (YcleanlinessRadioGroup.checkedRadioButtonId) {
            R.id.Y_cleanliness_sensitive -> 0
            R.id.Y_cleanliness_normal -> 1
            R.id.Y_cleanliness_not_sensitive -> 2
            else -> -1 // 선택된 값이 없는 경우 -1을 반환
        }
    }

    // 소음 민감도에 대한 선택된 값을 반환하는 함수
    private fun getSelectedNoiseSensitivity(): Int {
        return when (YnoiseSensitivityRadioGroup.checkedRadioButtonId) {
            R.id.Y_noise_sensitivity_sensitive -> 0
            R.id.Y_noise_sensitivity_normal -> 1
            R.id.Y_noise_sensitivity_not_sensitive -> 2
            else -> -1 // 선택된 값이 없는 경우 -1을 반환
        }
    }

    // 취침 시간에 대한 선택된 값을 반환하는 함수
    private fun getSelectedSleepSchedule(): Int {
        val firstGroupCheckedId = firstYSleepScheduleRadioGroup.checkedRadioButtonId
        val secondGroupCheckedId = secondYSleepScheduleRadioGroup.checkedRadioButtonId

        return when {
            firstGroupCheckedId != -1 -> { // 첫 번째 라디오 그룹에서 선택된 경우
                when (firstGroupCheckedId) {
                    R.id.Y_sleep_early -> 0
                    R.id.Y_sleep_ten -> 1
                    R.id.Y_sleep_normal -> 2
                    else -> -1 // 선택된 값이 없는 경우 -1을 반환
                }
            }
            secondGroupCheckedId != -1 -> { // 두 번째 라디오 그룹에서 선택된 경우
                when (secondGroupCheckedId) {
                    R.id.Y_sleep_late -> 3
                    R.id.Y_sleep_very_late -> 4
                    else -> -1 // 선택된 값이 없는 경우 -1을 반환
                }
            }
            else -> -1 // 선택된 값이 없는 경우 -1을 반환
        }
    }


}

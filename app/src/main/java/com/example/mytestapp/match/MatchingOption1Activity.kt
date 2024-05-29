package com.example.mytestapp.match

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mytestapp.R
import com.example.mytestapp.MainActivity
import com.example.mytestapp.databinding.ActivityMatchingOption1Binding

class MatchingOption1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchingOption1Binding
    private var Y_mValue: Int = -1
    private var Y_gradeValue: Int = -1
    private var Y_smokingValue: Int = -1
    private var Y_firstLessonValue: Int = -1
    private var Y_sleepingHabitValue: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchingOption1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // 메인으로 버튼 클릭 시 MainActivity로 이동
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 이전 버튼 클릭 시 MainActivity로 이동
        binding.btnPrev.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // 다음 버튼 클릭 시 선택한 값을 확인하고 처리
        binding.btnNext.setOnClickListener {
            if (isValuesSelected()) {
                // 선택한 값들을 다음 액티비티로 전달하고 해당 액티비티로 이동
                val intent = Intent(this, MatchingOption2Activity::class.java)
                intent.putExtra("Ym", Y_mValue)
                intent.putExtra("Ygrade", Y_gradeValue)
                intent.putExtra("Ysmoking", Y_smokingValue)
                intent.putExtra("YfirstLesson", Y_firstLessonValue)
                intent.putExtra("YsleepingHabit", Y_sleepingHabitValue)
                startActivity(intent)
                finish()
            } else {
                // 필수 정보를 선택하지 않았을 경우에 대한 처리
                Toast.makeText(this, "모든 선택지를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 외향형(E) / 내향형(I) 버튼
        binding.YME.setOnClickListener {
            Y_mValue = 1 // 외향형 선택 시 값 설정
            binding.YME.setBackgroundResource(R.drawable.option_bar_left_selected) // 선택된 상태 배경으로 변경
            binding.YMI.setBackgroundResource(R.drawable.option_bar_right) // 내향형 버튼의 배경 초기화
        }
        binding.YMI.setOnClickListener {
            Y_mValue = 0 // 내향형 선택 시 값 설정
            binding.YMI.setBackgroundResource(R.drawable.option_bar_right_selected) // 선택된 상태 배경으로 변경
            binding.YME.setBackgroundResource(R.drawable.option_bar_left) // 외향형 버튼의 배경 초기화
        }

        // 학년 선택 라디오 그룹에서 선택한 값을 변수에 저장
        binding.YGradeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            Y_gradeValue = when (checkedId) {
                R.id.Y_grade_1 -> 1
                R.id.Y_grade_2 -> 2
                R.id.Y_grade_3 -> 3
                R.id.Y_grade_4 -> 4
                else -> -1 // 선택하지 않았을 경우 -1로 설정
            }
        }

        // 흡연 여부 라디오 그룹에서 선택한 값을 변수에 저장
        binding.YSmokingRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            Y_smokingValue = if (checkedId == R.id.Y_smoking_yes) 1 else 0 // O는 1, X는 0으로 저장
        }

        // 1교시 유무 라디오 그룹에서 선택한 값을 변수에 저장
        binding.YFirstLessonRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            Y_firstLessonValue = if (checkedId == R.id.Y_first_lesson_yes) 1 else 0 // O는 1, X는 0으로 저장
        }

        // 잠버릇 유무 라디오 그룹에서 선택한 값을 변수에 저장
        binding.YSleepingHabitRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            Y_sleepingHabitValue = if (checkedId == R.id.Y_sleeping_habit_yes) 1 else 0 // O는 1, X는 0으로 저장
        }
    }

    private fun isValuesSelected(): Boolean {
        // 모든 값이 선택되었는지 확인하는 함수
        return (Y_gradeValue != -1 && Y_mValue != -1 && Y_smokingValue != -1 && Y_firstLessonValue != -1 && Y_sleepingHabitValue != -1)
    }
}

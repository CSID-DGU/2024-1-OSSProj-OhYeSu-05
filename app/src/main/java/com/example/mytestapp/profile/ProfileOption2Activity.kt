package com.example.mytestapp.profile

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import com.example.mytestapp.R
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mytestapp.MainActivity
import com.example.mytestapp.databinding.ActivityProfileOption2Binding

class ProfileOption2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileOption2Binding
    private var gradeValue: Int = -1 // 학년 값 저장 변수 초기화, 선택되지 않은 상태를 -1로 표시
    private var smokingValue: Int = -1 // 흡연 여부 값 저장 변수 초기화
    private var firstLessonValue: Int = -1 // 1교시 유무 값 저장 변수 초기화
    private var sleepingHabitValue: Int = -1 // 잠버릇 유무 값 저장 변수 초기화
    private var sharingDailyNeedsValue: Int = -1 // 생필품 공유 가능 여부 값 저장 변수 초기화

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileOption2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // "이전으로" 버튼 클릭 시 ProfileOption1Activity로 이동
        binding.btnPrev.setOnClickListener {
            startActivity(Intent(this, ProfileOption1Activity::class.java))
            finish()
        }

        // "메인으로" 버튼 클릭 시 MainActivity로 이동
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // "다음으로" 버튼 클릭 시 선택한 값 확인 후 ProfileOption3Activity로 이동
        binding.btnNext.setOnClickListener {
            if (isValuesSelected()) {
                val intent = Intent(this, ProfileOption3Activity::class.java)
                intent.putExtra("grade", gradeValue)
                intent.putExtra("smoking", smokingValue)
                intent.putExtra("firstLesson", firstLessonValue)
                intent.putExtra("sleepingHabit", sleepingHabitValue)
                intent.putExtra("sharingDailyNeeds", sharingDailyNeedsValue)
                startActivity(intent)
                finish()
            } else {
                // 필수 정보를 선택하지 않았을 경우에 대한 처리
                Toast.makeText(this, "모든 선택지를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 학년 선택 라디오 그룹에서 선택한 값을 변수에 저장
        binding.gradeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            gradeValue = when (checkedId) {
                R.id.grade_1 -> 1
                R.id.grade_2 -> 2
                R.id.grade_3 -> 3
                R.id.grade_4 -> 4
                else -> -1 // 선택하지 않았을 경우 -1로 설정
            }
        }

        // 흡연 여부 라디오 그룹에서 선택한 값을 변수에 저장
        binding.smokingRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            smokingValue = if (checkedId == R.id.smoking_yes) 1 else 0 // O는 1, X는 0으로 저장
        }

        // 1교시 유무 라디오 그룹에서 선택한 값을 변수에 저장
        binding.firstLessonRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            firstLessonValue = if (checkedId == R.id.firstlesson_yes) 1 else 0 // O는 1, X는 0으로 저장
        }

        // 잠버릇 유무 라디오 그룹에서 선택한 값을 변수에 저장
        binding.sleepingHabitRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            sleepingHabitValue = if (checkedId == R.id.sleepinghabit_yes) 1 else 0 // O는 1, X는 0으로 저장
        }

        // 생필품 공유 가능 여부 라디오 그룹에서 선택한 값을 변수에 저장
        binding.sharingDailyNeedsRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            sharingDailyNeedsValue = if (checkedId == R.id.sharing_yes) 1 else 0 // O는 1, X는 0으로 저장
        }
    }

    // 선택된 값이 모두 있는지 확인하는 함수
    private fun isValuesSelected(): Boolean {
        return gradeValue != -1 && smokingValue != -1 && firstLessonValue != -1 && sleepingHabitValue != -1 && sharingDailyNeedsValue != -1
    }
}

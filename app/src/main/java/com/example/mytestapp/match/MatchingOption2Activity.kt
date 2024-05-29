package com.example.mytestapp.match

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.mytestapp.R
import androidx.appcompat.app.AppCompatActivity
import com.example.mytestapp.MainActivity
import com.example.mytestapp.databinding.ActivityMatchingOption2Binding

class MatchingOption2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchingOption2Binding
    private var Y_sharingDailyNeedsValue: Int = -1 // 생필품 공유 가능 여부 값 저장 변수 초기화
    private var Y_internalCommunicationValueValue: Int = -1 // 룸메이트의 전화 통화 가능 여부 값 저장 변수 초기화
    private var Y_heatSensitiveValue: Int = -1 // 더위에 예민한 편 여부 값 저장 변수 초기화
    private var Y_coldSensitiveValue: Int = -1 // 추위에 예민한 편 여부 값 저장 변수 초기화

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchingOption2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // 메인으로 버튼 클릭 시 MainActivity로 이동
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 이전 버튼 클릭 시 MatchingOption1Activity로 이동
        binding.btnPrev.setOnClickListener {
            startActivity(Intent(this, MatchingOption1Activity::class.java))
            finish()
        }

        // 다음 버튼 클릭 시 선택한 값을 확인하고 처리
        binding.btnNext.setOnClickListener {
            if (isValuesSelected()) {
                // 선택한 값들을 다음 액티비티로 전달하고 해당 액티비티로 이동
                val intent = Intent(this, MatchingOption3Activity::class.java)
                intent.putExtra("Y_sharingDailyNeeds", Y_sharingDailyNeedsValue)
                intent.putExtra("Y_internalCommunication", Y_internalCommunicationValueValue)
                intent.putExtra("Y_heatSensitive", Y_heatSensitiveValue)
                intent.putExtra("Y_coldSensitive", Y_coldSensitiveValue)
                startActivity(intent)
                finish()
            } else {
                // 필수 정보를 선택하지 않았을 경우에 대한 처리
                Toast.makeText(this, "모든 선택지를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 각 라디오 버튼 그룹에 대한 리스너 설정 및 선택된 값 저장
        binding.YSharingDailyNeedsRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            Y_sharingDailyNeedsValue = if (checkedId == R.id.Y_sharing_yes) 1 else 0 // O는 1, X는 0으로 저장
        }

        binding.YInternalCommunicationRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            Y_internalCommunicationValueValue = if (checkedId == R.id.Y_internal_communication_yes) 1 else 0 // O는 1, X는 0으로 저장
        }

        binding.YHeatSensitiveRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            Y_heatSensitiveValue = if (checkedId == R.id.Y_heat_sensitive_yes) 1 else 0 // O는 1, X는 0으로 저장
        }

        binding.YColdSensitiveRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            Y_coldSensitiveValue = if (checkedId == R.id.Y_cold_sensitive_yes) 1 else 0 // O는 1, X는 0으로 저장
        }

    }

    private fun isValuesSelected(): Boolean {
        // 모든 값이 선택되었는지 확인하는 함수
        return (Y_sharingDailyNeedsValue != -1 && Y_internalCommunicationValueValue != -1 && Y_heatSensitiveValue != -1 && Y_coldSensitiveValue != -1)
    }
}

package com.example.mytestapp.profile

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.mytestapp.R
import androidx.appcompat.app.AppCompatActivity
import com.example.mytestapp.MainActivity
import com.example.mytestapp.databinding.ActivityProfileOption3Binding

class ProfileOption3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileOption3Binding
    // 변수 설정
    private var internalCommunicationValue: Int = -1
    private var heatSensitiveValue: Int = -1
    private var coldSensitiveValue: Int = -1
    private var drinkingFrequencyValue: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileOption3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // 이전 버튼 클릭 시 ProfileOption2Activity로 이동
        binding.btnPrev.setOnClickListener {
            startActivity(Intent(this, ProfileOption2Activity::class.java))
            finish()
        }

        // 메인으로 버튼 클릭 시 MainActivity로 이동
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 다음 버튼 클릭 시 선택한 값을 확인하고 처리
        binding.btnNext.setOnClickListener {
            if (isValuesSelected()) {
                // 선택한 값들을 다음 액티비티로 전달하고 해당 액티비티로 이동
                val intent = Intent(this, ProfileOption4Activity::class.java)
                intent.putExtra("internalCommunication", internalCommunicationValue)
                intent.putExtra("heatSensitive", heatSensitiveValue)
                intent.putExtra("coldSensitive", coldSensitiveValue)
                intent.putExtra("drinkingFrequency", drinkingFrequencyValue)
                startActivity(intent)
                finish()
            } else {
                // 필수 정보를 선택하지 않았을 경우에 대한 처리
                Toast.makeText(this, "모든 선택지를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 각 라디오 버튼 그룹에 대한 리스너 설정 및 선택된 값 저장
        binding.internalCommunicationRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            internalCommunicationValue = if (checkedId == R.id.internal_communication_yes) 1 else 0
        }

        binding.heatSensitiveRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            heatSensitiveValue = if (checkedId == R.id.heat_sensitive_yes) 1 else 0
        }

        binding.coldSensitiveRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            coldSensitiveValue = if (checkedId == R.id.cold_sensitive_yes) 1 else 0
        }

        binding.drinkingFrequencyRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            drinkingFrequencyValue = when (checkedId) {
                R.id.frequency_zero -> 0
                R.id.frequency_third -> 1
                else -> 2
            }
        }
    }

    private fun isValuesSelected(): Boolean {
        // 모든 값이 선택되었는지 확인을 위한 함수
        return (internalCommunicationValue != -1 && heatSensitiveValue != -1 && coldSensitiveValue != -1 && drinkingFrequencyValue != -1)
    }
}

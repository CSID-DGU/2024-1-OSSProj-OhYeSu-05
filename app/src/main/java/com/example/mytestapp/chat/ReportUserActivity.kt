package com.example.mytestapp.chat

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.mytestapp.R
import com.example.mytestapp.model.request.ReportData
import com.example.mytestapp.model.response.ReportResponse
import com.example.mytestapp.service.ChatService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID

class ReportUserActivity : Activity() {
    private lateinit var chatService: ChatService
    private lateinit var currentUserId: String
    private lateinit var targetUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_user) // 올바른 레이아웃 파일 참조

        targetUserId = intent.getStringExtra("targetUserId") ?: "unknown"

        initializeComponents()
    }

    private fun initializeComponents() {
        currentUserId = "currentUserId"

        val retrofit = Retrofit.Builder()
            .baseUrl("http://yourserver.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        chatService = retrofit.create(ChatService::class.java)

        showReportReasonDialog(targetUserId)
    }

    private fun showReportReasonDialog(reportedId: String) {
        val view = LayoutInflater.from(this).inflate(R.layout.report_dialog, null)
        val dialog = AlertDialog.Builder(this).setView(view).create()

        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroupReasons)
        val editTextOtherReason = view.findViewById<EditText>(R.id.editTextOtherReason)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            editTextOtherReason.visibility = if (checkedId == R.id.radioButtonOther) View.VISIBLE else View.GONE
        }

        view.findViewById<Button>(R.id.buttonSubmit).setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId

            if (selectedId == -1) {
                Toast.makeText(this, "신고 사유를 선택해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val reason = if (selectedId == R.id.radioButtonOther) {
                editTextOtherReason.text.toString().takeIf { it.isNotBlank() } ?: run {
                    Toast.makeText(this, "기타 사유를 입력해주세요", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            } else {
                view.findViewById<RadioButton>(selectedId).text.toString()
            }

            confirmReportUser(reportedId, reason)
            dialog.dismiss()
        }

        view.findViewById<Button>(R.id.buttonCancel).setOnClickListener {
            dialog.dismiss()
            finish()
        }

        dialog.show()
    }

    private fun confirmReportUser(reportedId: String, reason: String) {
        AlertDialog.Builder(this)
            .setTitle("사용자 신고")
            .setMessage("해당 사용자를 신고하시겠습니까?\n신고 사유: $reason")
            .setPositiveButton("예") { _, _ -> reportUser(reportedId, reason) }
            .setNegativeButton("아니오") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun reportUser(reportedId: String, reason: String) {
        val reportId = UUID.randomUUID().toString()
        val reportData = ReportData(
            ReportID = reportId,
            UserID = currentUserId,
            Reason = reason,
            ReporterID = currentUserId,
            ReportedID = reportedId
        )
        chatService.reportUser(reportData).enqueue(object : Callback<ReportResponse> {
            override fun onResponse(call: Call<ReportResponse>, response: Response<ReportResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.success) {
                            AlertDialog.Builder(this@ReportUserActivity)
                                .setTitle("신고 완료")
                                .setMessage("해당 사용자가 신고되었습니다.")
                                .setPositiveButton("확인") { dialog, _ ->
                                    dialog.dismiss()
                                    finish()
                                }
                                .show()
                        } else {
                            Toast.makeText(this@ReportUserActivity, "신고에 실패했습니다: ${it.error}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@ReportUserActivity, "신고에 실패했습니다", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                Toast.makeText(this@ReportUserActivity, "에러: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}

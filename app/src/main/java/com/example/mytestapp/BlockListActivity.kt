package com.example.mytestapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytestapp.adapter.BlockRecyclerViewAdapter
import com.example.mytestapp.databinding.ActivityBlockRecyclerviewBinding
import com.example.mytestapp.model.request.BlockData

class BlockListActivity : AppCompatActivity(), BlockRecyclerViewAdapter.OnItemClickListener {

    private lateinit var binding: ActivityBlockRecyclerviewBinding
    private lateinit var blockListAdapter: BlockRecyclerViewAdapter
    private var blockList: MutableList<BlockData> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlockRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 리사이클러뷰 어댑터 초기화
        blockListAdapter = BlockRecyclerViewAdapter(blockList, this)

        // 리사이클러뷰 설정
        binding.blockRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@BlockListActivity)
            adapter = blockListAdapter
        }
    }

    // 차단 해제 버튼 클릭 이벤트 처리
    override fun onUnblockButtonClick(blockItem: BlockData) {
        blockList.remove(blockItem) // 차단 목록에서 아이템 제거
        blockListAdapter.notifyDataSetChanged() // 어댑터에 변경 사항 알림
    }
}
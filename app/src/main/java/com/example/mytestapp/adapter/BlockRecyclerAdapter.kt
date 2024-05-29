package com.example.mytestapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestapp.R
import com.example.mytestapp.model.request.BlockData

// RecyclerView에 사용될 어댑터 클래스 정의
class BlockRecyclerViewAdapter(
    private val blockList: MutableList<BlockData>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<BlockRecyclerViewAdapter.BlockViewHolder>() {

    // 아이템 클릭 이벤트를 처리하기 위한 인터페이스 정의
    interface OnItemClickListener {
        fun onUnblockButtonClick(blockItem: BlockData)
    }

    // ViewHolder 클래스 정의
    inner class BlockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ViewHolder 내부의 뷰들을 참조
        private val blockedIDTextView: TextView = itemView.findViewById(R.id.BlockedIDTextView)
        private val unblockButton: Button = itemView.findViewById(R.id.unblockButton)

        // Unblock 버튼의 클릭 이벤트 처리
        init {
            unblockButton.setOnClickListener {
                val position = adapterPosition
                // 클릭된 아이템의 위치가 유효한지 확인 후, 리스너를 통해 아이템 클릭 이벤트 전달
                if (position != RecyclerView.NO_POSITION) {
                    listener.onUnblockButtonClick(blockList[position])
                }
            }
        }

        // ViewHolder에 데이터 바인딩
        fun bind(blockItem: BlockData) {
            blockedIDTextView.text = blockItem.BlockedID
            // 여기에 다른 데이터를 ViewHolder에 바인딩하는 코드 추가 가능
        }
    }

    // ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockViewHolder {
        // XML 레이아웃을 inflate하여 ViewHolder를 생성하고 반환
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_block_itemrecyclerview, parent, false)
        return BlockViewHolder(itemView)
    }

    // ViewHolder 데이터 바인딩
    override fun onBindViewHolder(holder: BlockViewHolder, position: Int) {
        holder.bind(blockList[position])
    }

    // 전체 아이템 수 반환
    override fun getItemCount() = blockList.size

    // 차단 해제 버튼을 클릭했을 때 호출되는 메서드
    fun unblockItem(position: Int) {
        blockList.removeAt(position) // 해당 아이템을 목록에서 제거
        notifyItemRemoved(position) // 어댑터에 변경 사항을 알림
    }
}

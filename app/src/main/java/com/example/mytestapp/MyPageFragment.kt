import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mytestapp.BlockListActivity
import com.example.mytestapp.R
import com.example.mytestapp.profile.ProfileOption1Activity

class MyPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mypage, container, false)

        // 받아온 데이터 추출
        val name = arguments?.getString("name")
        val studentId = arguments?.getString("studentId")

        // 학번과 이름을 표시할 TextView 찾기
        val studentIdTextView = view.findViewById<TextView>(R.id.StudentID)
        val nameTextView = view.findViewById<TextView>(R.id.Name)

        // 학번과 이름 설정
        studentIdTextView.text = studentId
        nameTextView.text = name

        // 프로필 수정 버튼 클릭 시
        view.findViewById<Button>(R.id.editProfileButton).setOnClickListener {
            // TODO: 프로필 수정 버튼 클릭 시 동작 구현
            // ProfileOption1Activity로 이동
            val intent = Intent(requireContext(), ProfileOption1Activity::class.java)
            startActivity(intent)
        }

        // 차단 목록 관리 버튼 클릭 시
        view.findViewById<Button>(R.id.blockListButton).setOnClickListener {
            // TODO: 차단 목록 관리 버튼 클릭 시 동작 구현
            // 차단 목록 액티비티를 열거나 프래그먼트 내에서 차단 목록을 관리하는 기능
            val intent = Intent(requireContext(), BlockListActivity::class.java)
            startActivity(intent)
        }

        // 로그아웃 버튼 클릭 시
        view.findViewById<Button>(R.id.logout).setOnClickListener {
            // 로그아웃 처리
            logout()
        }

        return view
    }

    private fun logout() {
        // 로그아웃 처리를 수행. 로그인 상태를 초기화하고 로그인 화면으로 이동
//        val intent = Intent(requireContext(), LoginActivity::class.java)
//        startActivity(intent)
//        requireActivity().finish() // 현재 액티비티 종료
        requireActivity().supportFragmentManager.popBackStack()
    }
}

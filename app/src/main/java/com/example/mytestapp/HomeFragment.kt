package com.example.mytestapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
//import com.example.mytestapp.create_room.CreateRoomLocalActivity
import com.example.mytestapp.databinding.FragmentHomeBinding
import com.example.mytestapp.match.MatchingOption1Activity
import com.example.mytestapp.profile.ProfileOption1Activity

class HomeFragment: Fragment(), View.OnClickListener  {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnProfile.setOnClickListener {
            activity?.let{
                val intent1 = Intent (it, ProfileOption1Activity::class.java)
                it.startActivity(intent1)
            }
        }
        binding.btnMatching.setOnClickListener {
            activity?.let{
                val intent = Intent (it, MatchingOption1Activity::class.java)
                it.startActivity(intent)
            }

        }
        setOnClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setOnClickListener() {
        val btnSequence = binding.container2.children
        btnSequence.forEach { btn ->
            btn.setOnClickListener(this)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_profile -> {
                val intent = Intent(getActivity(), ProfileOption1Activity::class.java)
                startActivity(intent)
            }
            R.id.btn_matching -> {
                val intent = Intent(getActivity(), MatchingOption1Activity::class.java)
                startActivity(intent)
            }
        }
    }
}



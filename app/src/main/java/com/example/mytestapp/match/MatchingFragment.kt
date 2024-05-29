package com.example.mytestapp.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytestapp.databinding.FragmentMatchingBinding
import com.example.mytestapp.viewmodel.MatchingViewModel
import com.example.mytestapp.adapter.MatchingAdapter

class MatchingFragment : Fragment() {
    private var _binding: FragmentMatchingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MatchingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MatchingViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = MatchingAdapter(viewModel)
        binding.rvMatchingList.adapter = adapter
        binding.rvMatchingList.layoutManager = LinearLayoutManager(context)

        // 데이터를 로드
        viewModel.loadMatchingProfiles(requireContext())

        viewModel.matchingProfiles.observe(viewLifecycleOwner) { profiles ->
            profiles?.let { adapter.submitList(it) }
        }

        binding.imageMenu.setOnClickListener {
            viewModel.onMenuButtonClicked(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

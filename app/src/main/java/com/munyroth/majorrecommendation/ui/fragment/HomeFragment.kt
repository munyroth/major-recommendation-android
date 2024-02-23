package com.munyroth.majorrecommendation.ui.fragment

import android.content.Intent
import com.munyroth.majorrecommendation.databinding.FragmentHomeBinding
import com.munyroth.majorrecommendation.ui.activity.MajorActivity
import com.munyroth.majorrecommendation.ui.activity.SelectClassStudyActivity
import com.munyroth.majorrecommendation.ui.activity.UniversityActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun initActions() {

    }

    override fun setupListeners() {
        binding.cardViewRecommendingMajors.setOnClickListener {
            val activityRecommendation = Intent(requireContext(), SelectClassStudyActivity::class.java)
            startActivity(activityRecommendation)
        }

        binding.cardViewMajors.setOnClickListener {
            val activityMajor = Intent(requireContext(), MajorActivity::class.java)
            startActivity(activityMajor)
        }

        binding.cardViewUniversities.setOnClickListener {
            val activityUniversity = Intent(requireContext(), UniversityActivity::class.java)
            startActivity(activityUniversity)
        }
    }

    override fun setupObservers() {

    }
}
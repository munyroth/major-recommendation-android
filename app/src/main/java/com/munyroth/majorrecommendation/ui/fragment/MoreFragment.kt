package com.munyroth.majorrecommendation.ui.fragment

import android.content.Intent
import com.munyroth.majorrecommendation.databinding.FragmentMoreBinding
import com.munyroth.majorrecommendation.ui.activity.ChangeLanguageActivity

class MoreFragment : BaseFragment<FragmentMoreBinding>(FragmentMoreBinding::inflate) {
    override fun initActions() {

    }

    override fun setupListeners() {
        binding.btnChangeLanguage.setOnClickListener {
            startActivity(Intent(requireContext(), ChangeLanguageActivity::class.java))
        }
    }

    override fun setupObservers() {

    }
}
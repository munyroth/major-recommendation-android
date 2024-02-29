package com.munyroth.majorrecommendation.ui.activity

import android.content.Intent
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.databinding.ActivitySelectClassStudyBinding

class SelectClassStudyActivity : BaseActivity<ActivitySelectClassStudyBinding>(
    ActivitySelectClassStudyBinding::inflate
) {
    override fun initActions() {
        supportActionBar?.apply {
            title = getString(R.string.title_major_recommendation)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun setupListeners() {
        binding.cardViewClassScience.setOnClickListener {
            val activityInputSubjectScoreScience = Intent(this, InputSubjectScoreScienceActivity::class.java)
            startActivity(activityInputSubjectScoreScience)
        }

        binding.cardViewClassSocial.setOnClickListener {
            val activityInputSubjectScoreSocial = Intent(this, InputSubjectScoreSocialActivity::class.java)
            startActivity(activityInputSubjectScoreSocial)
        }
    }

    override fun setupObservers() {

    }

}
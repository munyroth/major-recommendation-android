package com.munyroth.majorrecommendation.ui.activity

import android.content.Intent
import android.widget.EditText
import androidx.activity.viewModels
import com.google.gson.Gson
import com.munyroth.majorrecommendation.databinding.ActivityInputSubjectScoreSocialBinding
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.request.RecommendationRequest
import com.munyroth.majorrecommendation.utility.InputFilterMinMax
import com.munyroth.majorrecommendation.utility.InputScoreEditText
import com.munyroth.majorrecommendation.viewmodel.RecommendationViewModel


class InputSubjectScoreSocialActivity : BaseActivity<ActivityInputSubjectScoreSocialBinding>(
    ActivityInputSubjectScoreSocialBinding::inflate
) {
    companion object {
        private const val MAX_KHMER_SCORE = 125f
        private const val MAX_SUBJECT_SCORE = 75f
        private const val MAX_ENGLISH_SCORE = 50f
    }

    private val recommendationViewModel: RecommendationViewModel by viewModels()

    private lateinit var data: RecommendationRequest

    private val khmerEditText: InputScoreEditText by lazy { binding.edtKhmer }
    private val mathsEditText: InputScoreEditText by lazy { binding.edtMaths }
    private val historyEditText: InputScoreEditText by lazy { binding.edtHistory }
    private val geographyEditText: InputScoreEditText by lazy { binding.edtGeography }
    private val moralityEditText: InputScoreEditText by lazy { binding.edtMorality }
    private val englishEditText: InputScoreEditText by lazy { binding.edtEnglish }

    override fun initActions() {
        supportActionBar?.apply {
            title = "Recommendation"
            setDisplayHomeAsUpEnabled(true)
        }

        khmerEditText.filters = arrayOf(InputFilterMinMax(0f, MAX_KHMER_SCORE))
        englishEditText.filters = arrayOf(InputFilterMinMax(0f, MAX_ENGLISH_SCORE))
    }

    override fun setupListeners() {
        binding.btnNext.setOnClickListener {
            data = RecommendationRequest(
                khmer = khmerEditText.getFloatValueOrZero() / MAX_KHMER_SCORE,
                maths = mathsEditText.getFloatValueOrZero() / MAX_SUBJECT_SCORE,
                history = historyEditText.getFloatValueOrZero() / MAX_SUBJECT_SCORE,
                geography = geographyEditText.getFloatValueOrZero() / MAX_SUBJECT_SCORE,
                morality = moralityEditText.getFloatValueOrZero() / MAX_SUBJECT_SCORE,
                english = englishEditText.getFloatValueOrZero() / MAX_ENGLISH_SCORE
            )

            recommendationViewModel.recommendation(data)
        }
    }

    override fun setupObservers() {
        recommendationViewModel.recommendation.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    val activityResultRecommendation =
                        Intent(this, ResultRecommendationActivity::class.java)
                    val gson = Gson()
                    val json = gson.toJson(it.data?.data)

                    activityResultRecommendation.putExtra("data", json)
                    startActivity(activityResultRecommendation)
                }

                Status.ERROR -> {

                }

                Status.LOADING -> {

                }

                else -> {

                }
            }
        }
    }

    private fun EditText.getFloatValueOrZero(): Float {
        return this.text.toString().toFloatOrNull() ?: 0f
    }
}
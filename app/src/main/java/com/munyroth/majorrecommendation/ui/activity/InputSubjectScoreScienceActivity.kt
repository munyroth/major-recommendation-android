package com.munyroth.majorrecommendation.ui.activity

import android.content.Intent
import android.util.Log
import android.widget.EditText
import androidx.activity.viewModels
import com.google.gson.Gson
import com.munyroth.majorrecommendation.databinding.ActivityInputSubjectScoreScienceBinding
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.request.RecommendationRequest
import com.munyroth.majorrecommendation.utility.InputFilterMinMax
import com.munyroth.majorrecommendation.utility.InputScoreEditText
import com.munyroth.majorrecommendation.viewmodel.RecommendationViewModel


class InputSubjectScoreScienceActivity : BaseActivity<ActivityInputSubjectScoreScienceBinding>(
    ActivityInputSubjectScoreScienceBinding::inflate
) {
    companion object {
        private const val MAX_MATHS_SCORE = 125f
        private const val MAX_SUBJECT_SCORE = 75f
        private const val MAX_ENGLISH_SCORE = 50f
    }

    private val recommendationViewModel: RecommendationViewModel by viewModels()

    private lateinit var data: RecommendationRequest

    private val mathsEditText: InputScoreEditText by lazy { binding.edtMaths }
    private val khmerEditText: EditText by lazy { binding.edtKhmer }
    private val physicsEditText: EditText by lazy { binding.edtPhysics }
    private val chemistryEditText: EditText by lazy { binding.edtChemistry }
    private val biologyEditText: EditText by lazy { binding.edtBiology }
    private val englishEditText: EditText by lazy { binding.edtEnglish }

    override fun initActions() {
        supportActionBar?.apply {
            title = "Recommendation"
            setDisplayHomeAsUpEnabled(true)
        }

        mathsEditText.filters = arrayOf(InputFilterMinMax(0f, MAX_MATHS_SCORE))
        englishEditText.filters = arrayOf(InputFilterMinMax(0f, MAX_ENGLISH_SCORE))
    }

    override fun setupListeners() {
        binding.btnNext.setOnClickListener {
            data = RecommendationRequest(
                maths = mathsEditText.getFloatValueOrZero() / MAX_MATHS_SCORE,
                khmer = khmerEditText.getFloatValueOrZero() / MAX_SUBJECT_SCORE,
                physics = physicsEditText.getFloatValueOrZero() / MAX_SUBJECT_SCORE,
                chemistry = chemistryEditText.getFloatValueOrZero() / MAX_SUBJECT_SCORE,
                biology = biologyEditText.getFloatValueOrZero() / MAX_SUBJECT_SCORE,
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
                    Log.e("InputSubjectScoreScience", "Error: ${it.status}")
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
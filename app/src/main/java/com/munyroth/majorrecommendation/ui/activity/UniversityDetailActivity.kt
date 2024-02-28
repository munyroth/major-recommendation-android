package com.munyroth.majorrecommendation.ui.activity

import android.content.Intent
import android.net.Uri
import android.widget.ProgressBar
import androidx.activity.viewModels
import com.munyroth.majorrecommendation.databinding.ActivityUniversityDetailBinding
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.viewmodel.UniversityDetailViewModel

class UniversityDetailActivity : BaseActivity<ActivityUniversityDetailBinding>(
    ActivityUniversityDetailBinding::inflate
) {
    private val universityDetailViewModel: UniversityDetailViewModel by viewModels()

    private val progressBar: ProgressBar by lazy { binding.progressBar }
    override fun initActions() {
        // Get id, name, and logo from previous activity
        val intent = intent
        val id = intent.getIntExtra("university_id", 0)
        val name = intent.getStringExtra("university_name")
        val logo = intent.getStringExtra("university_logo")

        supportActionBar?.apply {
            title = name
            setDisplayHomeAsUpEnabled(true)
        }
        universityDetailViewModel.loadUniversity(id)
    }

    override fun setupListeners() {

    }

    override fun setupObservers() {
        universityDetailViewModel.university.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    progressBar.visibility = ProgressBar.VISIBLE
                }

                Status.SUCCESS -> {
                    progressBar.visibility = ProgressBar.GONE
                    binding.llDetail.visibility = ProgressBar.VISIBLE

                    val university = it.data?.data
                    binding.tvPhoneCall.text = university?.phone
                    binding.tvPhoneCall.setOnClickListener {
                        val intent =
                            Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + university?.phone))
                        startActivity(intent)
                    }

                    binding.tvEnvelope.text = university?.email
                    binding.tvEnvelope.setOnClickListener {
                        val intent =
                            Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + university?.email))
                        startActivity(intent)
                    }

                    binding.tvSiteBrowser.text = buildString {
                        append("https://")
                        append(university?.website)
                    }
                    binding.tvSiteBrowser.setOnClickListener {
                        val intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https:" + university?.website))
                        startActivity(intent)
                    }
                }

                Status.ERROR -> {
                    progressBar.visibility = ProgressBar.GONE
                }

                else -> {
                    progressBar.visibility = ProgressBar.GONE
                }
            }
        }
    }
}

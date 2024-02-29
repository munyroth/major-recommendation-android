package com.munyroth.majorrecommendation.ui.activity

import android.content.Intent
import android.net.Uri
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.munyroth.majorrecommendation.adapter.DynamicAdapter
import com.munyroth.majorrecommendation.databinding.ActivityUniversityDetailBinding
import com.munyroth.majorrecommendation.databinding.ViewHolderDepartmentBinding
import com.munyroth.majorrecommendation.databinding.ViewHolderFacultyBinding
import com.munyroth.majorrecommendation.model.Department
import com.munyroth.majorrecommendation.model.Faculty
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.viewmodel.UniversityDetailViewModel
import com.squareup.picasso.Picasso

class UniversityDetailActivity : BaseActivity<ActivityUniversityDetailBinding>(
    ActivityUniversityDetailBinding::inflate
) {
    companion object {
        const val EXTRA_UNIVERSITY_ID = "university_id"
        const val EXTRA_UNIVERSITY_NAME = "university_name"
        const val EXTRA_UNIVERSITY_LOGO = "university_logo"
    }

    private lateinit var adapterFaculty: DynamicAdapter<Faculty, ViewHolderFacultyBinding>
    private lateinit var adapterDepartment: DynamicAdapter<Department, ViewHolderDepartmentBinding>

    private val universityDetailViewModel: UniversityDetailViewModel by viewModels()
    private val progressBar: ProgressBar by lazy { binding.progressBar }

    override fun initActions() {
        // Get id, name, and logo from previous activity
        val id = intent.getIntExtra(EXTRA_UNIVERSITY_ID, 0)
        val name = intent.getStringExtra(EXTRA_UNIVERSITY_NAME)
        val logo = intent.getStringExtra(EXTRA_UNIVERSITY_LOGO)

        supportActionBar?.apply {
            title = name
            setDisplayHomeAsUpEnabled(true)
        }
        universityDetailViewModel.loadUniversity(id)
    }

    override fun setupListeners() {

    }

    override fun setupObservers() {
        universityDetailViewModel.university.observe(this) { resource ->
            when (resource.status) {
                Status.LOADING -> progressBar.visibility = ProgressBar.VISIBLE
                Status.SUCCESS -> {
                    progressBar.visibility = ProgressBar.GONE
                    binding.llDetail.visibility = ProgressBar.VISIBLE

                    val university = resource.data?.data
                    university?.let {
                        with(binding) {
                            Picasso.get().load(it.logo).into(ivUniversity)
                            tvUniversityName.text = it.nameEn
                            tvUniversityType.text = it.universityType
                            tvUniversityDescription.text = it.aboutEn

                            it.phone?.let { phone ->
                                tvPhoneCall.text = "0$phone"
                                tvPhoneCall.setOnClickListener { _ ->
                                    dialPhoneNumber(phone)
                                }
                            }

                            it.email?.let { email ->
                                tvEnvelope.text = email
                                tvEnvelope.setOnClickListener { _ ->
                                    sendEmail(email)
                                }
                            }

                            it.website?.let { website ->
                                tvSiteBrowser.text = website
                                tvSiteBrowser.setOnClickListener { _ ->
                                    openWebsite(website)
                                }
                            }
                        }

                        displayFaculties(it.faculties)
                    }
                }

                Status.ERROR -> progressBar.visibility = ProgressBar.GONE
                else -> progressBar.visibility = ProgressBar.GONE
            }
        }
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0$phoneNumber"))
        startActivity(intent)
    }

    private fun sendEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
        startActivity(intent)
    }

    private fun openWebsite(website: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https:$website"))
        startActivity(intent)
    }

    private fun displayFaculties(data: List<Faculty>?) {
        val linearLayout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvFaculty.layoutManager = linearLayout

        adapterFaculty =
            DynamicAdapter(ViewHolderFacultyBinding::inflate) { _, item, binding ->
                with(binding) {
                    tvName.text = item.nameEn
                }

                displayDepartments(binding, item.departments)
            }
        if (data != null) {
            adapterFaculty.setData(data)
        }
        binding.rvFaculty.adapter = adapterFaculty
    }

    private fun displayDepartments(binding: ViewHolderFacultyBinding, data: List<Department>?) {
        val linearLayout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvDepartment.layoutManager = linearLayout

        adapterDepartment =
            DynamicAdapter(ViewHolderDepartmentBinding::inflate) { view, item, departmentBinding ->
                view.setOnClickListener {

                }

                with(departmentBinding) {
                    tvName.text = item.nameEn
                }
            }

        if (data != null) {
            adapterDepartment.setData(data)
        }

        binding.rvDepartment.adapter = adapterDepartment
    }
}

package com.munyroth.majorrecommendation.ui.activity

import android.content.Intent
import android.os.Handler
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.munyroth.majorrecommendation.adapter.DynamicAdapter
import com.munyroth.majorrecommendation.databinding.ActivityUniversityBinding
import com.munyroth.majorrecommendation.databinding.ViewHolderUniversityBinding
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.model.University
import com.munyroth.majorrecommendation.viewmodel.UniversityViewModel
import com.squareup.picasso.Picasso

class UniversityActivity : BaseActivity<ActivityUniversityBinding>(ActivityUniversityBinding::inflate) {
    private lateinit var adapter: DynamicAdapter<University, ViewHolderUniversityBinding>

    private val universityViewModel: UniversityViewModel by viewModels()

    private val progressBar: ProgressBar by lazy { binding.progressBar }
    private val searchView: SearchView by lazy { binding.searchView }
    private val handler = Handler()

    override fun initActions() {
        supportActionBar?.apply {
            title = "Universities"
            setDisplayHomeAsUpEnabled(true)
        }

        universityViewModel.loadUniversities()
    }

    override fun setupListeners() {
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(search: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(search: String?): Boolean {

                // Use a delay before triggering the search
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    // Trigger the search operation with newText
                    performSearch(search)
                }, 300) // 300 milliseconds (0.3 seconds) delay

                return true
            }
        })
    }

    override fun setupObservers() {
        universityViewModel.universities.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    progressBar.visibility = ProgressBar.VISIBLE
                }

                Status.LOADING_MORE -> {
                    progressBar.visibility = ProgressBar.VISIBLE
                }

                Status.SUCCESS -> {
                    progressBar.visibility = ProgressBar.GONE
                    displayMajor(it.data?.data)
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

    private fun displayMajor(data: List<University>?) {
        // Create linear layout
        val linearLayout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayout;

        // Create adapter
        adapter = DynamicAdapter(ViewHolderUniversityBinding::inflate) { view, item, binding ->
            view.setOnClickListener {
                val intent = Intent(this, UniversityDetailActivity::class.java)
                intent.putExtra("university_id", item.id)
                intent.putExtra("university_name", item.nameEn)
                intent.putExtra("university_logo", item.logo)
                startActivity(intent)
            }

            with(binding) {
                Picasso.get().load(item.logo).into(img)
                txtTitle.text = item.nameEn
            }
        }
        if (data != null) {
            adapter.setData(data)
        }
        binding.recyclerView.adapter = adapter
    }

    // Function to perform the search
    private fun performSearch(search: String?) {
        displayMajor(null)

        if (search != null) {
            // Delegate the search operation to SearchViewHolder
            universityViewModel.loadUniversities(search)
        } else {
            // If the search is null, display no search results
            displayMajor(null)
        }
    }
}
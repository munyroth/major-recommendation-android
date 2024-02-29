package com.munyroth.majorrecommendation.ui.activity

import android.os.Handler
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.adapter.DynamicAdapter
import com.munyroth.majorrecommendation.databinding.ActivityMajorBinding
import com.munyroth.majorrecommendation.databinding.ViewHolderMajorBinding
import com.munyroth.majorrecommendation.model.Major
import com.munyroth.majorrecommendation.model.Status
import com.munyroth.majorrecommendation.viewmodel.MajorViewModel
import com.squareup.picasso.Picasso

class MajorActivity : BaseActivity<ActivityMajorBinding>(ActivityMajorBinding::inflate) {
    private lateinit var adapter: DynamicAdapter<Major, ViewHolderMajorBinding>

    private val majorViewModel: MajorViewModel by viewModels()

    private val progressBar: ProgressBar by lazy { binding.progressBar }
    private val searchView: SearchView by lazy { binding.searchView }
    private val handler = Handler()

    override fun initActions() {
        supportActionBar?.apply {
            title = getString(R.string.title_majors)
            setDisplayHomeAsUpEnabled(true)
        }

        majorViewModel.loadMajors()
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
        majorViewModel.majors.observe(this) {
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
                    progressBar.visibility = ProgressBar.GONE}
            }
        }
    }

    private fun displayMajor(data: List<Major>?) {
        // Create linear layout
        val linearLayout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayout;

        // Create adapter
        adapter = DynamicAdapter(ViewHolderMajorBinding::inflate) { view, item, binding ->
            view.setOnClickListener {

            }

            with(binding) {
                Picasso.get().load("https://i.pinimg.com/originals/e3/c1/8a/e3c18aa096cc01b16b7a3bae1da6aee0.jpg").into(img)
                txtTitle.text = item.major
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
            majorViewModel.loadMajors(search)
        } else {
            // If the search is null, display no search results
            displayMajor(null)
        }
    }
}
package com.munyroth.majorrecommendation.ui.activity

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.munyroth.majorrecommendation.R
import com.munyroth.majorrecommendation.adapter.DynamicAdapter
import com.munyroth.majorrecommendation.databinding.ActivityResultRecommendationBinding
import com.munyroth.majorrecommendation.databinding.ViewHolderResultRecommendationBinding
import com.munyroth.majorrecommendation.model.Recommendation
import com.squareup.picasso.Picasso

class ResultRecommendationActivity : BaseActivity<ActivityResultRecommendationBinding>(
    ActivityResultRecommendationBinding::inflate
) {
    private lateinit var adapter: DynamicAdapter<Recommendation, ViewHolderResultRecommendationBinding>

    override fun initActions() {
        supportActionBar?.apply {
            title = getString(R.string.title_major_recommendation)
            setDisplayHomeAsUpEnabled(true)
        }

        val gson = Gson()
        val json = intent.getStringExtra("data")
        val arrayType = object : TypeToken<List<Recommendation>>() {}.type
        val dataList: List<Recommendation>? = gson.fromJson(json, arrayType)

        displayMajor(dataList)
    }

    override fun setupListeners() {
        binding.btnFinish.setOnClickListener {
            val activityMain= Intent(this, MainActivity::class.java)
            activityMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(activityMain)
            finish()
        }
    }

    override fun setupObservers() {

    }

    private fun displayMajor(data: List<Recommendation>?) {
        // Create linear layout
        val linearLayout =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayout;

        // Create adapter
        adapter =
            DynamicAdapter(ViewHolderResultRecommendationBinding::inflate) { view, item, binding ->
                view.setOnClickListener {

                }

                with(binding) {
                    Picasso.get()
                        .load("https://i.pinimg.com/originals/e3/c1/8a/e3c18aa096cc01b16b7a3bae1da6aee0.jpg")
                        .into(img)
                    txtTitle.text = item.major
                    txtProbability.text = item.probability
                }
            }
        if (data != null) {
            adapter.setData(data)
        }
        binding.recyclerView.adapter = adapter
    }
}
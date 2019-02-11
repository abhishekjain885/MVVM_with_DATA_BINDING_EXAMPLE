package com.example.apple.minidemo.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.apple.minidemo.BR
import com.example.apple.minidemo.R
import com.example.apple.minidemo.dagger.AndroidInjection
import com.example.apple.minidemo.model.SourceItem
import com.example.apple.minidemo.network.IAPIHandler
import com.example.apple.minidemo.utils.SOURCE_OBJECT
import com.example.apple.minidemo.viewModel.NewsSourceViewModel
import javax.inject.Inject

class NewsSourcesActivity : AppCompatActivity() {

    @Inject
    lateinit var apiHandler: IAPIHandler
    private var viewModel: NewsSourceViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.applicationComponent()!!.inject(this@NewsSourcesActivity)
        setupBindings(savedInstanceState)
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        val activityBinding = DataBindingUtil.setContentView<ViewDataBinding>(
            this@NewsSourcesActivity,
            R.layout.activity_news_source
        )
        viewModel = ViewModelProviders.of(this).get(NewsSourceViewModel::class.java)
        if (savedInstanceState == null) {
            viewModel!!.init()
        }
        activityBinding.setVariable(BR.model, viewModel)
        setupListUpdate()

    }

    private fun setupListUpdate() {
        viewModel!!.loading!!.set(View.VISIBLE)
        viewModel!!.fetchList(apiHandler)

        viewModel!!.getNewsSources().observe(this,
            Observer<List<SourceItem>> { t ->
                viewModel!!.loading!!.set(View.GONE)
                if (t!!.isEmpty()) {
                    viewModel!!.showEmpty!!.set(View.VISIBLE)
                } else {
                    viewModel!!.showEmpty!!.set(View.GONE)
                    viewModel!!.setNewsSourceAdapter(t)
                }
            })

        setupListClick()
    }

    private fun setupListClick() {
        viewModel!!.getSelected()!!.observe(this,
            Observer<SourceItem> { item ->
                if (item != null) {
                    Toast.makeText(
                        this@NewsSourcesActivity,
                        "You selected " + item.name,
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@NewsSourcesActivity, ArticleListActivity::class.java)
                    intent.putExtra(SOURCE_OBJECT, item)
                    startActivity(intent)
                }
            })

    }
}

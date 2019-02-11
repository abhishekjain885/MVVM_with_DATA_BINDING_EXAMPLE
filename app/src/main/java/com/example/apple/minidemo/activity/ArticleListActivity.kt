package com.example.apple.minidemo.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.apple.minidemo.BR
import com.example.apple.minidemo.R
import com.example.apple.minidemo.dagger.AndroidInjection
import com.example.apple.minidemo.model.ArticleItem
import com.example.apple.minidemo.model.SourceItem
import com.example.apple.minidemo.network.IAPIHandler
import com.example.apple.minidemo.utils.SOURCE_OBJECT
import com.example.apple.minidemo.viewModel.ArticleListViewModel
import kotlinx.android.synthetic.main.search_bar.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*
import javax.inject.Inject

class ArticleListActivity : AppCompatActivity() {

    @Inject
    lateinit var apiHandler: IAPIHandler
    private var articleviewModel: ArticleListViewModel? = null
    private var sourceItem: String = ""
    private var timer: Timer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.applicationComponent()!!.inject(this@ArticleListActivity)
        setupBindings(savedInstanceState)
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        val activityBinding = DataBindingUtil.setContentView<ViewDataBinding>(
            this@ArticleListActivity,
            R.layout.activity_article_list
        )

        articleviewModel = ViewModelProviders.of(this).get(ArticleListViewModel::class.java)
        if (savedInstanceState == null) {
            articleviewModel!!.init()
        }
        activityBinding.setVariable(BR.articleViewModel, articleviewModel)
        sourceItem = intent.extras.getParcelable<SourceItem>(SOURCE_OBJECT).id!!

        initViews()
        setupListUpdate()
    }

    private fun initViews() {
        toolbar_title.text = sourceItem.toUpperCase() + "ARTICLES"
        bt_clear_text.setOnClickListener {
            if (!etSearch2.text.isEmpty()) {
                etSearch2.text.clear()
                val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
                articleviewModel!!.fetchList(apiHandler, sourceItem)
            }
        }

        etSearch2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterArticles(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (timer != null) {
                    timer?.cancel()
                }
            }
        })
    }

    private fun filterArticles(s: String) {

        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                val temp = articleviewModel!!.getArticlesSources().value
                val newList: ArrayList<ArticleItem> = ArrayList()
                if (temp != null) {
                    for (d in temp) {
                        if ((d.title!!.toLowerCase()).contains(s.toLowerCase())) {
                            newList.add(d)
                        }
                    }
                    if (newList.size > 0) {
                        articleviewModel!!.updateList(newList)
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@ArticleListActivity, "No Match Found", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }, 600)
    }

    private fun setupListUpdate() {
        articleviewModel!!.loading!!.set(View.VISIBLE)
        articleviewModel!!.fetchList(apiHandler, sourceItem)

        articleviewModel!!.getArticlesSources().observe(this,
            Observer<List<ArticleItem>> { t ->
                articleviewModel!!.loading!!.set(View.GONE)
                if (t!!.isEmpty()) {
                    articleviewModel!!.showEmpty!!.set(View.VISIBLE)
                } else {
                    articleviewModel!!.showEmpty!!.set(View.GONE)
                    articleviewModel!!.setArticleAdapter(t)
                }
            })

        setupListClick()
    }

    private fun setupListClick() {
        articleviewModel!!.getSelected()!!.observe(this,
            Observer<ArticleItem> { t ->
                if (t != null) {
                    Toast.makeText(
                        this@ArticleListActivity,
                        "You clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@ArticleListActivity, ArticleDisplayActivity::class.java)
                    intent.putExtra(SOURCE_OBJECT, t)
                    startActivity(intent)
                }
            })
    }

}

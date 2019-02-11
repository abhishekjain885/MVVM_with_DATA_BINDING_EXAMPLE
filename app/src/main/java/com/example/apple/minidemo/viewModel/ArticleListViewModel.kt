package com.example.apple.minidemo.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableInt
import android.view.View
import com.example.apple.minidemo.R
import com.example.apple.minidemo.adapter.ArticleAdapter
import com.example.apple.minidemo.model.ArticleItem
import com.example.apple.minidemo.model.ArticleSourceList
import com.example.apple.minidemo.network.IAPIHandler

class ArticleListViewModel : ViewModel() {

    private var articleSourceList: ArticleSourceList? = null

    private var adapter: ArticleAdapter? = null
    private var selected: MutableLiveData<ArticleItem>? = null
    // var lateinit images: ObservableArrayMap<String, String>
    var loading: ObservableInt? = null
    var showEmpty: ObservableInt? = null

    fun init() {
        articleSourceList = ArticleSourceList()
        selected = MutableLiveData()
        adapter = ArticleAdapter(R.layout.view_article_item, this)
        //images = ObservableArrayMap()
        loading = ObservableInt(View.GONE)
        showEmpty = ObservableInt(View.GONE)
    }

    fun fetchList(apiHandler: IAPIHandler, source_id: String) {
        articleSourceList!!.fetchArticles(apiHandler, source_id)
    }

    fun updateList(sources: List<ArticleItem>) {
        articleSourceList!!.updateArticleList(sources)
    }

    fun getArticlesSources(): MutableLiveData<List<ArticleItem>> {
        return articleSourceList!!.getArticleList()
    }

    fun getAdapter(): ArticleAdapter? {
        return adapter
    }

    fun setArticleAdapter(sources: List<ArticleItem>) {
        this.adapter!!.setArticles(sources)
        this.adapter!!.notifyDataSetChanged()
    }


    fun getSelected(): MutableLiveData<ArticleItem>? {
        return selected
    }

    fun onItemClick(index: Int?) {
        val db = getArticleAt(index)
        selected!!.setValue(db)
    }

    fun getArticleAt(index: Int?): ArticleItem? {
        return if (articleSourceList!!.getArticleList().getValue() != null &&
            index != null &&
            articleSourceList!!.getArticleList().value!!.size > index
        ) {
            articleSourceList!!.getArticleList().value!![index]
        } else null
    }

}
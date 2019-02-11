package com.example.apple.minidemo.model

import android.arch.lifecycle.MutableLiveData
import android.databinding.BaseObservable
import android.util.Log
import com.example.apple.minidemo.network.IAPIHandler
import com.example.apple.minidemo.utils.API_KEY
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import java.util.ArrayList

class ArticleSourceList : BaseObservable() {

    private var status: String? = null
    private val articles = ArrayList<ArticleItem>()
    private val livedataArticleList = MutableLiveData<List<ArticleItem>>()


    fun getArticleList(): MutableLiveData<List<ArticleItem>> {
        return livedataArticleList
    }

    fun updateArticleList(sources: List<ArticleItem>) {
        if (sources.isNotEmpty()) {
            livedataArticleList.postValue(sources)
        }
    }

    public fun fetchArticles(apiHandler: IAPIHandler, source_id: String) {

        apiHandler.getArticleList(source_id, API_KEY)
            .enqueue(object : retrofit2.Callback<ArticleSourceList> {
                override fun onFailure(call: Call<ArticleSourceList>, t: Throwable) {
                    Log.d("ArticleSourceList", "fail")
                }

                override fun onResponse(call: Call<ArticleSourceList>, response: Response<ArticleSourceList>) {
                    val body = response.body()
                    // status = body!!.status
                    livedataArticleList.setValue(body!!.articles)
                }
            })
    }
}
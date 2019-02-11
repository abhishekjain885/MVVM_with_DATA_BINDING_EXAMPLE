package com.example.apple.minidemo.model

import android.arch.lifecycle.MutableLiveData
import android.databinding.BaseObservable
import android.util.Log
import com.example.apple.minidemo.network.IAPIHandler
import com.example.apple.minidemo.utils.API_KEY
import com.example.apple.minidemo.utils.LANGUAGE
import retrofit2.Call
import retrofit2.Response

import java.util.ArrayList

class NewsSource : BaseObservable() {


    private var status: String? = null
    private val sources = ArrayList<SourceItem>()
    private val livedataSourcesList = MutableLiveData<List<SourceItem>>()

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun addSource(bd: SourceItem) {
        sources.add(bd)
    }

    fun UpdateSource(sources: List<SourceItem>) {
        livedataSourcesList.value = sources
    }

    fun getSources(): MutableLiveData<List<SourceItem>> {
        return livedataSourcesList
    }

    public fun fetchList(apiHandler: IAPIHandler) {

        apiHandler.getNewsSourceList(LANGUAGE, API_KEY)
            .enqueue(object : retrofit2.Callback<NewsSource> {
                override fun onFailure(call: Call<NewsSource>, t: Throwable) {
                    Log.d("getNewsSourceList", "fail")
                }

                override fun onResponse(call: Call<NewsSource>, response: Response<NewsSource>) {
                    val body = response.body()
                    status = body!!.status
                    livedataSourcesList.value = body.sources
                }
            })
    }


}

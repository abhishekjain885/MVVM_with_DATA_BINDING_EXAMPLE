package com.example.apple.minidemo.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableInt
import android.view.View
import com.example.apple.minidemo.R
import com.example.apple.minidemo.adapter.SourceAdapter
import com.example.apple.minidemo.model.NewsSource
import com.example.apple.minidemo.model.SourceItem
import com.example.apple.minidemo.network.IAPIHandler

class NewsSourceViewModel : ViewModel() {

    private var newsSources: NewsSource? = null
    private var adapter: SourceAdapter? = null
    private var selected: MutableLiveData<SourceItem>? = null
    // var lateinit images: ObservableArrayMap<String, String>
    var loading: ObservableInt? = null
    var showEmpty: ObservableInt? = null

    fun init() {
        newsSources = NewsSource()
        selected = MutableLiveData()
        adapter = SourceAdapter(R.layout.view_source_item, this)
        //images = ObservableArrayMap()
        loading = ObservableInt(View.GONE)
        showEmpty = ObservableInt(View.GONE)
    }

    fun fetchList(apiHandler: IAPIHandler) {
        newsSources!!.fetchList(apiHandler)
    }

    fun getNewsSources(): MutableLiveData<List<SourceItem>> {
        return newsSources!!.getSources()
    }

    fun getAdapter(): SourceAdapter? {
        return adapter
    }

    fun setNewsSourceAdapter(sources: List<SourceItem>) {
        this.adapter!!.setNewsSources(sources)
        this.adapter!!.notifyDataSetChanged()
    }

    fun getSelected(): MutableLiveData<SourceItem>? {
        return selected
    }

    fun onItemClick(index: Int?) {
        val db = getSourceAt(index)
        selected!!.setValue(db)
    }

    fun getSourceAt(index: Int?): SourceItem? {
        return if (newsSources!!.getSources().getValue() != null &&
            index != null &&
            newsSources!!.getSources().value!!.size > index
        ) {
            newsSources!!.getSources().value!!.get(index)
        } else null
    }
}
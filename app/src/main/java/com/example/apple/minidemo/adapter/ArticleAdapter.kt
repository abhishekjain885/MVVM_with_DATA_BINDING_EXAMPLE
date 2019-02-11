package com.example.apple.minidemo.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.apple.minidemo.BR
import com.example.apple.minidemo.model.ArticleItem
import com.example.apple.minidemo.viewModel.ArticleListViewModel


class ArticleAdapter() : RecyclerView.Adapter<ArticleAdapter.GenericViewHolder>() {

    var layoutId: Int = 0
    private var articleList: List<ArticleItem>? = null
    lateinit var viewModel: ArticleListViewModel


    constructor(@LayoutRes layoutId: Int, viewModel: ArticleListViewModel) : this() {
        this.layoutId = layoutId
        this.viewModel = viewModel
    }

    private fun getLayoutIdForPosition(position: Int): Int {
        return layoutId
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.GenericViewHolder {
        val layoutInflater = LayoutInflater.from(parent.getContext())
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return GenericViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (articleList == null) 0 else articleList!!.size
    }


    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    override fun onBindViewHolder(holder: ArticleAdapter.GenericViewHolder, position: Int) {
        holder.bind(viewModel, position)
    }

    fun setArticles(sources: List<ArticleItem>) {
        this.articleList = sources
    }

    class GenericViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: ArticleListViewModel, position: Int?) {
            binding.setVariable(BR.articleViewModel, viewModel)
            binding.setVariable(BR.position, position)
            binding.executePendingBindings()
        }

    }

}
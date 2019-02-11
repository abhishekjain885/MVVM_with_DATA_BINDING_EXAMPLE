package com.example.apple.minidemo.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.apple.minidemo.model.SourceItem
import com.example.apple.minidemo.viewModel.NewsSourceViewModel
import com.example.apple.minidemo.BR

class SourceAdapter() : RecyclerView.Adapter<SourceAdapter.GenericViewHolder>() {

    var layoutId: Int = 0
    private var newsSources: List<SourceItem>? = null
    lateinit var viewModel: NewsSourceViewModel


    constructor(@LayoutRes layoutId: Int, viewModel: NewsSourceViewModel) : this() {
        this.layoutId = layoutId
        this.viewModel = viewModel
    }

    private fun getLayoutIdForPosition(position: Int): Int {
        return layoutId
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceAdapter.GenericViewHolder {
        val layoutInflater = LayoutInflater.from(parent.getContext())
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return GenericViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (newsSources == null) 0 else newsSources!!.size
    }


    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    override fun onBindViewHolder(holder: SourceAdapter.GenericViewHolder, position: Int) {
        holder.bind(viewModel, position)
    }

    fun setNewsSources(sources: List<SourceItem>) {
        this.newsSources = sources
    }

    class GenericViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: NewsSourceViewModel, position: Int?) {
            binding.setVariable(BR.viewModel, viewModel)
            binding.setVariable(BR.position, position)
            binding.executePendingBindings()
        }

    }

}
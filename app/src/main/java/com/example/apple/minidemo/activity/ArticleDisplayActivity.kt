package com.example.apple.minidemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.apple.minidemo.R
import com.example.apple.minidemo.model.ArticleItem
import com.example.apple.minidemo.utils.SOURCE_OBJECT
import kotlinx.android.synthetic.main.activity_article_display.*
import kotlinx.android.synthetic.main.toolbar.*

class ArticleDisplayActivity : AppCompatActivity() {

    var sourceItem: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_display)
        sourceItem = intent.extras.getParcelable<ArticleItem>(SOURCE_OBJECT).url!!
        toolbar_title.text = sourceItem
        webview?.settings?.javaScriptEnabled = true
        webview?.loadUrl(sourceItem)
        webview?.isHorizontalScrollBarEnabled = false
    }
}

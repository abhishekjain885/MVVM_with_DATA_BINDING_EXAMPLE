package com.example.apple.minidemo.network

import com.example.apple.minidemo.model.ArticleSourceList
import com.example.apple.minidemo.model.NewsSource
import retrofit2.Call
import retrofit2.http.*


interface IAPIHandler {

    @GET(ENDPOINT_GET_NEWS_SOURCE_LIST)
    fun getNewsSourceList(@Query("language") language: String, @Query("apiKey") apiKey: String): Call<NewsSource>

    @GET(ENDPOINT_GET_ARTICLE_LIST)
    fun getArticleList(@Query("sources") language: String, @Query("apiKey") apiKey: String): Call<ArticleSourceList>


    companion object {
        const val ENDPOINT_GET_NEWS_SOURCE_LIST = "sources"

        const val ENDPOINT_GET_ARTICLE_LIST = "everything"
    }

}
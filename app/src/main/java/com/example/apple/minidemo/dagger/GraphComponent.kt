package com.example.apple.minidemo.dagger

import com.example.apple.minidemo.activity.ArticleListActivity
import com.example.apple.minidemo.activity.NewsSourcesActivity
import com.example.apple.minidemo.application.MiniApplication
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [DaggerModule::class])
interface GraphComponent {

    fun inject(application: MiniApplication)

    fun inject(mainActivity: NewsSourcesActivity)

    fun inject(articleListActivity: ArticleListActivity)


}
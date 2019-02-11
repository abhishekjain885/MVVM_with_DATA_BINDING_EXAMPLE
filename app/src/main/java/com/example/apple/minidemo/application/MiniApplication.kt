package com.example.apple.minidemo.application

import android.app.Application
import com.example.apple.minidemo.dagger.AndroidInjection

class MiniApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        configureDagger()
    }

    private fun configureDagger() {
        AndroidInjection.initialize(this)
        AndroidInjection.applicationComponent()!!.inject(this)
    }
}
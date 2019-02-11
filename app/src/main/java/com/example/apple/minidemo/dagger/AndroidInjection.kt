package com.example.apple.minidemo.dagger

import com.example.apple.minidemo.application.MiniApplication


object AndroidInjection {

    private var applicationComponent: GraphComponent? = null

    fun initialize(miniApplication: MiniApplication) {
        applicationComponent = DaggerGraphComponent.builder().daggerModule(DaggerModule(miniApplication)).build()
    }

    fun applicationComponent(): GraphComponent? {
        return applicationComponent
    }

}
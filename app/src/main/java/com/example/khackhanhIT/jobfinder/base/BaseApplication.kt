package com.example.khackhanhIT.jobfinder.base

import com.example.khackhanhIT.jobfinder.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication() {  // giup Dagger 2 hoat dong voi kotlin

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        val component = DaggerApplicationComponent.builder().application(this).build()

        component.inject(this)

        return component
    }
}
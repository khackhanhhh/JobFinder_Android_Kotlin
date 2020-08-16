package com.example.khackhanhIT.jobfinder.di.module

import com.example.khackhanhIT.jobfinder.ui.expand.ExpandActivity
import com.example.khackhanhIT.jobfinder.ui.main.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
 abstract class ActivityBindingModule {

 @ContributesAndroidInjector(modules = [AppModule::class, NetworkModule::class])
 internal abstract fun bindMainActivity(): MainActivity

 @ContributesAndroidInjector(modules = [AppModule::class, NetworkModule::class])
 internal abstract fun bindExpandActivity(): ExpandActivity



}
package com.example.khackhanhIT.jobfinder.di.component

import android.app.Application
import com.example.khackhanhIT.jobfinder.base.BaseApplication
import com.example.khackhanhIT.jobfinder.di.module.ActivityBindingModule
import com.example.khackhanhIT.jobfinder.di.module.MainFragmentBindingModule
import com.example.khackhanhIT.jobfinder.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class,
        MainFragmentBindingModule::class
    )
)
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: BaseApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }


}

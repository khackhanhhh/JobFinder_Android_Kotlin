package com.example.khackhanhIT.jobfinder.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import butterknife.ButterKnife
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    @LayoutRes
    protected abstract fun layoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) { // lan dau se la null sau khi nhap  luu thong tin cua nhung gi da kiem tra truoc
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())

        ButterKnife.bind(this)    } // rang buoc de tim kiem
}

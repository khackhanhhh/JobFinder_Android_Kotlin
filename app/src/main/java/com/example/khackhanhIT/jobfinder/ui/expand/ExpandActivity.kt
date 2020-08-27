package com.example.khackhanhIT.jobfinder.ui.expand

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.khackhanhIT.jobfinder.R
import com.example.khackhanhIT.jobfinder.base.BaseActivity
import kotlinx.android.synthetic.main.activity_expand.*

class ExpandActivity: BaseActivity() { // hien thi item tren layout
    override fun layoutRes(): Int {
        return R.layout.activity_expand
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val intent = intent


        Glide.with(this)
            .load(intent.getStringExtra("c_logo"))
            .into(image_company_logo)

        expand_activity_job_title.text = intent.getStringExtra("job_title")

        expand_activity_company_name.text = intent.getStringExtra("c_name")

        expand_activity_location.text = intent.getStringExtra("location")

        expand_activity_post_date.text = intent.getStringExtra("post_date")

        expand_activity_discription.text = intent.getStringExtra("description")






    }
}
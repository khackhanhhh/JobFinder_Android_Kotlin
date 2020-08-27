package com.example.khackhanhIT.jobfinder.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.khackhanhIT.jobfinder.R
import com.example.khackhanhIT.jobfinder.base.BaseActivity
import com.example.khackhanhIT.jobfinder.data.model.Job
import com.example.khackhanhIT.jobfinder.utils.NetworkUtility
import com.example.khackhanhIT.jobfinder.utils.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity: BaseActivity() {

    private var provider: String = ""
    private var query: String = ""
    private var description: String = ""
    private var location: String = ""
    private var jobPosition: String = ""


    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MainActivityViewModel

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }


    override fun onCreate(savedInstanceState: Bundle?) { // set layout xu ly
        super.onCreate(savedInstanceState)


        search_view.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                search_view.focusable = android.view.View.FOCUSABLE
            }

        }

        addProiverSpinner() // thêm drop downlist cho cái phía github
        addPostionSpinner() // thêm dropDownList cho cái full time và contact

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)


        search_button.setOnClickListener {

            getRequiredParameters()

        }


        if (NetworkUtility.isNetworkConnected(this)) {
            search_and_filter.visibility = View.VISIBLE
            no_network.visibility = View.GONE
            observableViewModel()
        } else {
            Log.d("RESPONSES:", "No network")
            search_and_filter.visibility = View.GONE
            no_network.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }


    }

    private fun addProiverSpinner() { // khoi tao va xy ly khi chon down list (github)
        val adapter =
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                resources.getStringArray(R.array.jobProviders) //github or GOV
            )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_provider.adapter = adapter
        provider = spinner_provider.selectedItem.toString()
        spinner_provider?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                provider = adapter.getItem(position)!!.toString()

            }
        }
    }

    private fun addPostionSpinner() { // khoi tao va xu ly khi chon down list (fulltime)
        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.position))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_position.adapter = adapter
        provider = spinner_position.selectedItem.toString()
        spinner_position?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                jobPosition = adapter.getItem(position)!!.toString()

            }
        }
    }

    private fun getRequiredParameters() { // lay params nhap vao o 2 o search & location

        if (!search_view.text.trim().isEmpty()) { // kiem tra va lay parametor
            description = search_view.text.toString()
            query = description

        }

        if (!search_location_view.text.trim().isEmpty()) {
            location = search_location_view.text.toString()
            query = "$query in the $location"
        }
        jobPosition = if (jobPosition == "FullTime") {
            "true"
        } else {
            "false"
        }

        Log.e("check:", "$provider, $query, $description, $location, $jobPosition")
        searchViewmodel() // query data


    }

    private fun searchViewmodel() { //query data, truyen data cho cai thang recycler view
        if (NetworkUtility.isNetworkConnected(this)) {
            search_and_filter.visibility = View.VISIBLE
            no_network.visibility = View.GONE


            viewModel.getJobSearched(provider, query, description, location, jobPosition).observe(this,
                Observer<List<Job>> {
                    if (it!!.isEmpty()) {
                        Toast.makeText(this, "No item found", Toast.LENGTH_SHORT).show()
                        recyclerView.visibility = View.GONE
                    } else {
                        setValueInRecyclerView(it) // set data recycler view
                    }
                })
        } else {
            Log.d("RESPONSES:", "No network")
            search_and_filter.visibility = View.GONE
            recyclerView.visibility = View.GONE
            no_network.visibility = View.VISIBLE
        }
    }


    private fun observableViewModel() {

        viewModel.getJobSearches().observe(this,  // fectch  data
            Observer<List<Job>> {
                setValueInRecyclerView(it!!) // set data to adapter job -> list<job>
            })

    }

    private fun setValueInRecyclerView(jobs: List<Job>){ // set data to recycler view
          no_network.visibility = View.GONE // an no network
          recyclerView.visibility = View.VISIBLE // show thang recycelview len
          recyclerView.layoutManager = LinearLayoutManager(this) // tao doi tuong cha chua recycelview
          recyclerView.setHasFixedSize(false) //
          recyclerView.itemAnimator = DefaultItemAnimator()
          val jobAdapter = JobsAdapter(this,this,jobs) // set apdapter JobAdapter to recycelview for 1 item in list
          recyclerView.adapter = jobAdapter
          jobAdapter.notifyDataSetChanged()

    }




}
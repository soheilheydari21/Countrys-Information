package com.heydari.usingretrofitandmvvm.view

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.heydari.usingretrofitandmvvm.R
import com.heydari.usingretrofitandmvvm.databinding.ActivityMainBinding
import com.heydari.usingretrofitandmvvm.helper.CountryListAdapter
import com.heydari.usingretrofitandmvvm.model.CountryModel
import com.heydari.usingretrofitandmvvm.viewModel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    var listOfSearch = ArrayList<CountryModel>()
    lateinit var recyclerAdapter: CountryListAdapter
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            binding.progressBar.visibility = VISIBLE
            binding.textView.visibility = GONE
            binding.button.visibility = GONE
            initRecyclerView()
            initViewModel()
        }

        binding.progressBar.visibility = VISIBLE
        title = "Country information"
        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView() {
        binding.countryListRecyclerview.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = CountryListAdapter(this)
        binding.countryListRecyclerview.adapter = recyclerAdapter
    }

    private fun initViewModel() {
        val viewModel: MainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this, {
            if (it != null) {
                recyclerAdapter.setCountryList(it)
                recyclerAdapter.notifyDataSetChanged()
                binding.textView.visibility = GONE
                binding.button.visibility = GONE
                binding.progressBar.visibility = GONE
            }
            else {
                binding.textView.visibility = VISIBLE
                binding.button.visibility = VISIBLE
                binding.progressBar.visibility = GONE
            }
        })
        viewModel.makeAPICall()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.serach_menu, menu)
        val searchView = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //ToDo Search
                filter(newText!!)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    fun filter(text:String) {
        val filteredList: ArrayList<CountryModel> = ArrayList()
        for (item in listOfSearch) {
            if (item.country.toLowerCase().contains(text.toLowerCase()))
                filteredList.add(item)
        }
        recyclerAdapter.filterList(filteredList)
    }


}
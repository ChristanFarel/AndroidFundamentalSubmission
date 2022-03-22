package com.example.sub1githubuser.ui.activity

import android.app.SearchManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sub1githubuser.ItemsItem
import com.example.sub1githubuser.R
import com.example.sub1githubuser.SearchResponse
import com.example.sub1githubuser.database.remote.retrofit.ApiConfig
import com.example.sub1githubuser.databinding.ActivityMainBinding
import com.example.sub1githubuser.ui.adapter.ListGHuserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    companion object{
        const val rcv = "RECYCLER"
    }

    private lateinit var arrayData: ArrayList<ItemsItem>
    private lateinit var binding: ActivityMainBinding
    private lateinit var rcyUser: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rcyUser = findViewById(R.id.rcyUser)
        rcyUser.setHasFixedSize(true)

//        if (savedInstanceState != null) {
//            savedInstanceState.getParcelableArrayList<ItemsItem>(rcv)?.let { showRecyclerList(it) }
//        }else{
//            findUser("")
//        }
        findUser("")
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putParcelableArrayList(rcv, arrayData)
//    }

    private fun findUser(cariUser: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getUser(cariUser)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.items.isEmpty()){
                            showEror()
                        }else{
                            binding.textEror.text = ""
                        }
                        showRecyclerList(responseBody.items)
                    }else{
                        showEror()
                    }
                } else {
                    showEror()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                showEror()
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showEror(){
        binding.textEror.text = getString(R.string.pesan_error_main)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private  fun showRecyclerList(isi: ArrayList<ItemsItem>){
        rcyUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListGHuserAdapter(isi)
        arrayData = isi
        rcyUser.adapter  = listUserAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                findUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.setting -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                return true
            }

            R.id.fav -> {
                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }


}
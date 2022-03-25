package com.example.sub1githubuser.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sub1githubuser.ItemsItem
import com.example.sub1githubuser.R
import com.example.sub1githubuser.databinding.ActivityMainBinding
import com.example.sub1githubuser.ui.adapter.ListGHuserAdapter
import com.example.sub1githubuser.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var arrayData: ArrayList<ItemsItem>
    private lateinit var binding: ActivityMainBinding
    private val viewModelMain: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.ghUser_Home_title)

        viewModelMain.showEror.observe(this, {
            showEror(it)
        })

        viewModelMain.showLoading.observe(this, {
            showLoading(it)
        })

        viewModelMain.listUser.observe(this, {
            showRecyclerList(it)
        })

    }

    private fun showEror(er: String) {
        binding.textEror.text = er
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showRecyclerList(isi: ArrayList<ItemsItem>) {
        binding.rcyUser.setHasFixedSize(true)
        binding.rcyUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListGHuserAdapter(isi)
        arrayData = isi
        binding.rcyUser.adapter = listUserAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView =
            menu.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModelMain.findUser(query)
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
        when (item.itemId) {
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
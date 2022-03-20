package com.example.sub1githubuser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFragment : Fragment() {

    private lateinit var rcyFollower: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(SectionsPagerAdapter.key)
        Log.d("username", username.toString())
//        findFollower(username.toString())
        rcyFollower = view.findViewById(R.id.rcy_follower)
        rcyFollower.setHasFixedSize(true)
        findFollower(username.toString())

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)

    }

    private  fun showRecyclerList(isi: ArrayList<FollowResponseItem>){
        rcyFollower.layoutManager = LinearLayoutManager(requireActivity())
        val listFolAdapter = FolAdapter(isi)
        rcyFollower.adapter  = listFolAdapter

    }

    private fun findFollower(cariFollower: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollower(cariFollower)
        client.enqueue(object : Callback<ArrayList<FollowResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<FollowResponseItem>>,
                response: Response<ArrayList<FollowResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        showRecyclerList(responseBody)
                    }
                } else {
                    Log.e("coba1", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ArrayList<FollowResponseItem>>, t: Throwable) {
                showLoading(false)
                Log.e("Coba2", "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        val pb = view?.findViewById<ProgressBar>(R.id.progressBar2)
        if (isLoading) {
           pb?.visibility = View.VISIBLE
        } else {
            pb?.visibility = View.GONE
        }
    }

}
package com.example.sub1githubuser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
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
        rcyFollower = view.findViewById(R.id.rcy_follower)
        rcyFollower.setHasFixedSize(true)
        findFollower(username.toString())

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                        if(responseBody.isEmpty()){
                            showError()
                        }else{
                            val txtErrorFollower = view?.findViewById<TextView>(R.id.FollowerTextError)
                            txtErrorFollower?.text = ""
                        }
                        showRecyclerList(responseBody)
                    }else{
                        showError()
                    }
                } else {
                    showError()
                    Log.e("LogPertama", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ArrayList<FollowResponseItem>>, t: Throwable) {
                showError()
                showLoading(false)
                Log.e("LogKedua", "onFailure: ${t.message}")
            }
        })
    }

    fun showError(){
        val txtErrorFollower = view?.findViewById<TextView>(R.id.FollowerTextError)
        txtErrorFollower?.text = "Tidak Memiliki Follower/Internet Buruk"
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
package com.example.sub1githubuser.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sub1githubuser.FollowResponseItem
import com.example.sub1githubuser.R
import com.example.sub1githubuser.database.remote.retrofit.ApiConfig
import com.example.sub1githubuser.databinding.FragmentFollowersBinding
import com.example.sub1githubuser.ui.adapter.FolAdapter
import com.example.sub1githubuser.ui.adapter.SectionsPagerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(SectionsPagerAdapter.key)

        findFollower(username.toString())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding?.root

    }

    private fun showRecyclerList(isi: ArrayList<FollowResponseItem>) {
        binding?.rcyFollower?.setHasFixedSize(true)
        binding?.rcyFollower?.layoutManager = LinearLayoutManager(requireActivity())
        binding?.rcyFollower?.adapter = FolAdapter(isi)

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
                        if (responseBody.isEmpty()) {
                            showError()
                        } else {
                            binding?.FollowerTextError?.text = ""
                        }
                        showRecyclerList(responseBody)
                    } else {
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

    fun showError() {
        binding?.FollowerTextError?.text = getString(R.string.pesan_errro)
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
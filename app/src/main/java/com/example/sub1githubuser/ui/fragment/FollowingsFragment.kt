package com.example.sub1githubuser.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sub1githubuser.FollowResponseItem
import com.example.sub1githubuser.R
import com.example.sub1githubuser.database.remote.retrofit.ApiConfig
import com.example.sub1githubuser.databinding.FragmentFollowingsBinding
import com.example.sub1githubuser.ui.adapter.FolAdapter
import com.example.sub1githubuser.ui.adapter.SectionsPagerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowingsFragment : Fragment() {

    private var _binding: FragmentFollowingsBinding? = null
    private val binding get() = _binding
    private lateinit var rcyFollower: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(SectionsPagerAdapter.key)

        findFollower(username.toString())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingsBinding.inflate(inflater, container, false)
        return binding?.root

    }

    private fun showRecyclerList(isi: ArrayList<FollowResponseItem>) {
        binding?.rcyFollowing?.setHasFixedSize(true)
        binding?.rcyFollowing?.layoutManager = LinearLayoutManager(requireActivity())
        binding?.rcyFollowing?.adapter = FolAdapter(isi)
    }

    private fun findFollower(cariFollowing: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollowing(cariFollowing)
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
                            binding?.FollowingTextError?.text = ""
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
        binding?.FollowingTextError?.text = getString(R.string.error_following)
    }

    private fun showLoading(isLoading: Boolean) {
        val pb = view?.findViewById<ProgressBar>(R.id.progressBar3)
        if (isLoading) {
            pb?.visibility = View.VISIBLE
        } else {
            pb?.visibility = View.GONE
        }
    }


}
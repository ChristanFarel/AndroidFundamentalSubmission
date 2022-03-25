package com.example.sub1githubuser.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sub1githubuser.ui.fragment.FollowersFragment
import com.example.sub1githubuser.ui.fragment.FollowingsFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    companion object {
        var username = ""
        var key = "KEY"
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowersFragment()
                val mBundle = Bundle()
                mBundle.putString(key, username)
                fragment.arguments = mBundle
            }

            1 -> {
                fragment = FollowingsFragment()
                val mBundle = Bundle()
                mBundle.putString(key, username)
                fragment.arguments = mBundle
            }
        }
        return fragment as Fragment
    }

}
package com.example.hanwha_kimdahye.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerFragmentStateAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    var fragments: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(fm: Fragment) {
        fragments.add(fm)
        notifyItemInserted(fragments.size - 1)
    }
}
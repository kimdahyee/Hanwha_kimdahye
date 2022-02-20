package com.example.hanwha_kimdahye.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hanwha_kimdahye.databinding.FragmentFavoritesBinding
import com.example.hanwha_kimdahye.ui.adapter.PagerFragmentStateAdapter
import com.example.hanwha_kimdahye.ui.view.bookmark.BookmarkCompanyFragment
import com.example.hanwha_kimdahye.ui.view.bookmark.BookmarkNewsFragment
import com.google.android.material.tabs.TabLayoutMediator

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding!!

    private val tabTitle = arrayOf("뉴스", "IR / 공시")

    private val pagerAdapter by lazy { PagerFragmentStateAdapter(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagerAdapter.addFragment(BookmarkNewsFragment())
        pagerAdapter.addFragment(BookmarkCompanyFragment())
        binding.viewPagerFavorites.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayoutFavorites, binding.viewPagerFavorites) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

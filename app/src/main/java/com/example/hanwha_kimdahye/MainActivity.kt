package com.example.hanwha_kimdahye

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.hanwha_kimdahye.databinding.ActivityMainBinding
import com.example.hanwha_kimdahye.ui.adapter.PagerFragmentStateAdapter
import com.example.hanwha_kimdahye.ui.view.CategorySelectFragment
import com.example.hanwha_kimdahye.ui.view.FavoritesFragment
import com.example.hanwha_kimdahye.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val pagerAdapter by lazy { PagerFragmentStateAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel

        binding.viewPagerMain.adapter = pagerAdapter
        pagerAdapter.addFragment(CategorySelectFragment())
        pagerAdapter.addFragment(FavoritesFragment())
    }
}

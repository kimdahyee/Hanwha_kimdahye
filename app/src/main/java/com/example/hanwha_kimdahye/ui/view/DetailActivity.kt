package com.example.hanwha_kimdahye.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.hanwha_kimdahye.R
import com.example.hanwha_kimdahye.databinding.ActivityDetailBinding
import com.example.hanwha_kimdahye.ui.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val binding =
            DataBindingUtil.setContentView<ActivityDetailBinding>(
                this,
                R.layout.activity_detail
            )
        binding.lifecycleOwner = this
        val intent = intent
        detailViewModel.handleIntent(intent)
        binding.viewModel = detailViewModel
    }
}

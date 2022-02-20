package com.example.hanwha_kimdahye.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hanwha_kimdahye.R
import com.example.hanwha_kimdahye.databinding.FragmentCategorySelectBinding
import com.example.hanwha_kimdahye.ui.view.search.CompanySearchActivity
import com.example.hanwha_kimdahye.ui.view.search.NewsSearchActivity
import com.example.hanwha_kimdahye.ui.viewmodel.CategorySelectViewModel
import com.example.hanwha_kimdahye.ui.viewmodel.eventObserve

class CategorySelectFragment : Fragment() {

    private lateinit var binding: FragmentCategorySelectBinding
    private val categorySelectViewModel: CategorySelectViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_category_select, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = categorySelectViewModel
        initObserve()
        return binding.root
    }

    private fun initObserve() {
        categorySelectViewModel.openEvent.eventObserve(this) {
            val intent: Intent = when (it) {
                1 -> Intent(this.context, NewsSearchActivity::class.java)
                else -> Intent(this.context, CompanySearchActivity::class.java)
            }
            intent.putExtra("index", it)
            startActivity(intent)
        }
    }
}

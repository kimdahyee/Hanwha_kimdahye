package com.example.hanwha_kimdahye.ui.view

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.hanwha_kimdahye.R
import com.example.hanwha_kimdahye.databinding.ActivityCompanySearchBinding
import com.example.hanwha_kimdahye.ui.adapter.CompanySearchAdapter
import com.example.hanwha_kimdahye.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompanySearchActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModels()
    private val companySearchAdapter by lazy { CompanySearchAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_search)
        init()
        searchCompany()
    }

    private fun init() {
        val binding =
            DataBindingUtil.setContentView<ActivityCompanySearchBinding>(
                this,
                R.layout.activity_company_search
            )
        binding.lifecycleOwner = this
        val intent = intent
        searchViewModel.handleIntent(intent)
        binding.viewModel = searchViewModel
        binding.rcvCompanySearch.adapter = companySearchAdapter
        binding.btnCompanySearch.setOnClickListener {
            lifecycleScope.launch {
                companySearchAdapter.submitData(PagingData.empty())
                searchCompany()
            }
        }
    }

    private var job: Job? = null

    private fun searchCompany() {
        hideKeyboard()
        job?.cancel()
        job = lifecycleScope.launch {
            searchViewModel.requestNewsSearch(searchViewModel.searchQuery.value.toString())
                .collectLatest {
                    companySearchAdapter.submitData(it)
                }
        }
    }

    private fun hideKeyboard() {
        currentFocus?.run {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(windowToken, 0)
        }
    }
}

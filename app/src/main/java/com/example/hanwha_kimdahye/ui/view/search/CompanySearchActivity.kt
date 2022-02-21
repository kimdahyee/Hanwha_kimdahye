package com.example.hanwha_kimdahye.ui.view.search

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.hanwha_kimdahye.R
import com.example.hanwha_kimdahye.databinding.ActivityCompanySearchBinding
import com.example.hanwha_kimdahye.ui.LoadStateAdapter
import com.example.hanwha_kimdahye.ui.adapter.search.CompanySearchAdapter
import com.example.hanwha_kimdahye.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompanySearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompanySearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private val companySearchAdapter by lazy { CompanySearchAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setViews()
        searchCompany()
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_company_search)
        binding.lifecycleOwner = this
        binding.viewModel = searchViewModel
        binding.rcvCompanySearch.adapter = companySearchAdapter
        val intent = intent
        searchViewModel.handleIntent(intent)
    }

    private fun setViews() {
        companySearchAdapter.addLoadStateListener { loadState ->
            binding.tvNothing.isVisible =
                loadState.refresh is LoadState.NotLoading && companySearchAdapter.itemCount == 0 && binding.etCompanySearch.text.isNotEmpty()
        }

        lifecycleScope.launch {
            companySearchAdapter.loadStateFlow.collectLatest {
                binding.progressBarCompanySearch.isVisible = it.append is LoadState.Loading
            }
        }

        binding.btnCompanySearch.setOnClickListener {
            lifecycleScope.launch {
                companySearchAdapter.submitData(PagingData.empty())
                searchCompany()
            }
        }

        binding.etCompanySearch.setOnEditorActionListener { _, action, _ ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_DONE) {
                lifecycleScope.launch {
                    companySearchAdapter.submitData(PagingData.empty())
                    searchCompany()
                }
                handled = true
            }
            handled
        }
    }

    private var job: Job? = null

    private fun searchCompany() {
        hideKeyboard()
        job?.cancel()
        job = lifecycleScope.launch {
            var q = searchViewModel.searchQuery.value.toString()
            if (q.isEmpty()) { q = "한화" }
            searchViewModel.requestNewsSearch(q)
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

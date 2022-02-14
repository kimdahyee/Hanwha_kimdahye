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
import com.example.hanwha_kimdahye.databinding.ActivityNewsSearchBinding
import com.example.hanwha_kimdahye.ui.adapter.NewsSearchAdapter
import com.example.hanwha_kimdahye.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsSearchActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModels()
    private val newsSearchAdapter by lazy { NewsSearchAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        searchNews()
    }

    private fun init() {
        val binding =
            DataBindingUtil.setContentView<ActivityNewsSearchBinding>(
                this,
                R.layout.activity_news_search
            )
        binding.lifecycleOwner = this
        val intent = intent
        searchViewModel.handleIntent(intent)
        binding.viewModel = searchViewModel
        binding.rcvNewsSearch.adapter = newsSearchAdapter
        binding.btnNewsSearch.setOnClickListener {
            lifecycleScope.launch {
                newsSearchAdapter.submitData(PagingData.empty())
                searchNews()
            }
        }
    }

    private var job: Job? = null

    private fun searchNews() {
        hideKeyboard()
        job?.cancel()
        job = lifecycleScope.launch {
            var q = searchViewModel.searchQuery.value.toString()
            if (q.isEmpty()) { q = "한화" }
            searchViewModel.requestNewsSearch(q)
                .collectLatest {
                    newsSearchAdapter.submitData(it)
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

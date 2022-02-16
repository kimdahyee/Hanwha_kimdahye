package com.example.hanwha_kimdahye.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import com.example.hanwha_kimdahye.R
import com.example.hanwha_kimdahye.data.model.Docs
import com.example.hanwha_kimdahye.databinding.ActivityNewsSearchBinding
import com.example.hanwha_kimdahye.ui.LoadStateAdapter
import com.example.hanwha_kimdahye.ui.adapter.NewsSearchAdapter
import com.example.hanwha_kimdahye.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private val newsSearchAdapter by lazy { NewsSearchAdapter() }
    private var newsList = mutableListOf<Docs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setViews()
        searchNews()
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_search)
        binding.lifecycleOwner = this
        val intent = intent
        searchViewModel.handleIntent(intent)
        binding.viewModel = searchViewModel
        binding.rcvNewsSearch.adapter = newsSearchAdapter.withLoadStateFooter(
            footer = LoadStateAdapter { newsSearchAdapter.retry() }
        )
    }

    private fun setViews() {
        newsSearchAdapter.addLoadStateListener { loadState ->
            binding.tvNothing.isVisible =
                loadState.refresh is LoadState.NotLoading && newsSearchAdapter.itemCount == 0 && binding.etNewsSearch.text.isNotEmpty()
        }
        binding.btnNewsSearch.setOnClickListener {
            lifecycleScope.launch {
                newsSearchAdapter.submitData(PagingData.empty())
                searchNews()
            }
        }

        newsSearchAdapter.setItemClickListener(object : NewsSearchAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, docs: Docs) {
                onItemClick(docs)
            }
        })
    }

    private var job: Job? = null

    private fun searchNews() {
        hideKeyboard()
        job?.cancel()
        job = lifecycleScope.launch {
            var q = searchViewModel.searchQuery.value.toString()
            if (q.isEmpty()) {
                q = "한화"
            }
            searchViewModel.requestNewsSearch(q)
                .collectLatest { it ->
                    newsSearchAdapter.submitData(it)
                    it.map { it2 ->
                        newsList.add(it2)
                    }
                }
        }
    }

    private fun onItemClick(news: Docs) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("title", news.title)
        intent.putExtra("publisher", news.publisher)
        intent.putExtra("author", news.author)
        intent.putExtra("imageUrl", news.imageUrls[0])
        intent.putExtra("content", news.content)
        startActivity(intent)
    }

    private fun hideKeyboard() {
        currentFocus?.run {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(windowToken, 0)
        }
    }
}

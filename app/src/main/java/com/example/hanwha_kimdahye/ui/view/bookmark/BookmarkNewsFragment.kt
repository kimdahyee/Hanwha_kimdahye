package com.example.hanwha_kimdahye.ui.view.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hanwha_kimdahye.R
import com.example.hanwha_kimdahye.data.model.Docs
import com.example.hanwha_kimdahye.databinding.FragmentBookmarkNewsBinding
import com.example.hanwha_kimdahye.ui.adapter.bookmark.BookmarkNewsAdapter
import com.example.hanwha_kimdahye.ui.adapter.search.NewsSearchAdapter
import com.example.hanwha_kimdahye.ui.view.NewsDetailActivity
import com.example.hanwha_kimdahye.ui.viewmodel.BookmarkViewModel

class BookmarkNewsFragment : Fragment() {

    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private val bookmarkNewsAdapter by lazy { BookmarkNewsAdapter() }
    private lateinit var binding: FragmentBookmarkNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_bookmark_news, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = bookmarkViewModel
        binding.rcvBookmarkNews.adapter = bookmarkNewsAdapter
        bookmarkViewModel.getBookmarkNewsResult(requireContext(), "news")
        observeBookmarkNews()
        return binding.root
    }

    private fun observeBookmarkNews() {
        bookmarkViewModel.bookmarks.observe(viewLifecycleOwner) {
            bookmarkNewsAdapter.setItem(it)
            bookmarkNewsAdapter.setItemClickListener(object : BookmarkNewsAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int) {
                    onItemClick(it[position])
                }
            })
        }
    }

    private fun onItemClick(news: Docs) {
        val url = if (news.imageUrls.isEmpty()) {
            null
        } else {
            news.imageUrls[0]
        }

        val intent = Intent(requireContext(), NewsDetailActivity::class.java)
        intent.putExtra("title", news.title)
        intent.putExtra("publisher", news.publisher)
        intent.putExtra("author", news.author)
        intent.putExtra("imageUrl", url)
        intent.putExtra("content", news.content)
        startActivity(intent)
    }
}

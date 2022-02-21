package com.example.hanwha_kimdahye.ui.view.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hanwha_kimdahye.R
import com.example.hanwha_kimdahye.databinding.FragmentBookmarkNewsBinding
import com.example.hanwha_kimdahye.ui.adapter.bookmark.BookmarkNewsAdapter
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
        }
    }
}

package com.example.hanwha_kimdahye.ui.view.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hanwha_kimdahye.R
import com.example.hanwha_kimdahye.databinding.FragmentBookmarkCompanyBinding
import com.example.hanwha_kimdahye.ui.adapter.bookmark.BookmarkCompanyAdapter
import com.example.hanwha_kimdahye.ui.viewmodel.BookmarkViewModel

class BookmarkCompanyFragment : Fragment() {

    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private val bookmarkCompanyAdapter by lazy { BookmarkCompanyAdapter() }
    private lateinit var binding: FragmentBookmarkCompanyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_bookmark_company, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = bookmarkViewModel
        binding.rcvBookmarkCompany.adapter = bookmarkCompanyAdapter
        bookmarkViewModel.getBookmarkNewsResult(requireContext(), "company")
        observeBookmarkCompany()
        return binding.root
    }

    private fun observeBookmarkCompany() {
        bookmarkViewModel.bookmarks.observe(viewLifecycleOwner) {
            bookmarkCompanyAdapter.setItem(it)
        }
    }
}

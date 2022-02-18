package com.example.hanwha_kimdahye.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hanwha_kimdahye.R
import com.example.hanwha_kimdahye.data.database.BookmarkDatabase
import com.example.hanwha_kimdahye.data.model.Bookmark
import com.example.hanwha_kimdahye.data.model.Docs
import com.example.hanwha_kimdahye.databinding.ItemCompanyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompanySearchAdapter :
    PagingDataAdapter<Docs, CompanySearchAdapter.CompanySearchViewHolder>(SearchDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanySearchViewHolder =
        CompanySearchViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_company,
                parent,
                false
            ),
            parent.context
        )

    override fun onBindViewHolder(holder: CompanySearchViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class CompanySearchViewHolder(
        private val binding: ItemCompanyBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(company: Docs) {
            binding.company = company
            var count: Int
            val db = BookmarkDatabase.getInstance(context)
            CoroutineScope(Dispatchers.IO).launch {
                count = db!!.bookmarkDao().initCheck(company.uid)
                if (count == 1) {
                    bookmarkedPage()
                } else {
                    notBookmarkedPage()
                }
            }
            binding.btnCompanyBookmark.setOnClickListener { setBookmarkButtonClickEvent(company) }
        }

        private fun bookmarkedPage() {
            binding.btnCompanyBookmark.isSelected = true
        }

        private fun notBookmarkedPage() {
            binding.btnCompanyBookmark.isSelected = false
        }

        private fun setBookmarkButtonClickEvent(company: Docs) {
            if (getBookmarkButtonStatus()) {
                val db = BookmarkDatabase.getInstance(context)
                CoroutineScope(Dispatchers.IO).launch {
                    db!!.bookmarkDao().delete(company.uid)
                }
                setBookmarkButtonStatus(false)
                return
            }

            val url = if (company.imageUrls.isEmpty()) {
                null
            } else {
                company.imageUrls[0]
            }

            val bookmarkedCompany = Bookmark(
                company.uid,
                company.category,
                company.section,
                company.publisher,
                company.author,
                company.title,
                company.content,
                url,
                company.contentUrl
            )

            val db = BookmarkDatabase.getInstance(context)
            CoroutineScope(Dispatchers.IO).launch {
                db!!.bookmarkDao().insert(bookmarkedCompany)
            }
            setBookmarkButtonStatus(true)
        }

        private fun setBookmarkButtonStatus(bookmarked: Boolean) {
            if (bookmarked) {
                bookmarkedPage()
                return
            }
            notBookmarkedPage()
        }

        private fun getBookmarkButtonStatus(): Boolean = binding.btnCompanyBookmark.isSelected
    }

    class SearchDiffCallBack : DiffUtil.ItemCallback<Docs>() {
        override fun areItemsTheSame(oldItem: Docs, newItem: Docs): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Docs, newItem: Docs): Boolean {
            return oldItem == newItem
        }
    }
}

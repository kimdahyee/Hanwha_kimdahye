package com.example.hanwha_kimdahye.ui.adapter.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hanwha_kimdahye.R
import com.example.hanwha_kimdahye.data.database.BookmarkDatabase
import com.example.hanwha_kimdahye.data.model.Docs
import com.example.hanwha_kimdahye.databinding.ItemCompanyBinding
import com.example.hanwha_kimdahye.ui.view.WebViewActivity
import com.example.hanwha_kimdahye.util.DiffUtilItemCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompanySearchAdapter :
    PagingDataAdapter<Docs, CompanySearchAdapter.CompanySearchViewHolder>(DiffUtilItemCallback()) {

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
            binding.tvContentUrl.setOnClickListener { showWebView(company) }
        }

        private fun showWebView(company: Docs) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("section", company.section)
            intent.putExtra("webUrl", company.contentUrl)
            context.startActivity(intent)
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

            val db = BookmarkDatabase.getInstance(context)
            CoroutineScope(Dispatchers.IO).launch {
                db!!.bookmarkDao().insert(company)
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
}

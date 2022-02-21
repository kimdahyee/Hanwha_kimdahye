package com.example.hanwha_kimdahye.ui.adapter.bookmark

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hanwha_kimdahye.data.database.BookmarkDatabase
import com.example.hanwha_kimdahye.data.model.Docs
import com.example.hanwha_kimdahye.databinding.ItemCompanyBinding
import com.example.hanwha_kimdahye.ui.view.WebViewActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created By kimdahyee
 * on 02월 20일, 2020
 */

class BookmarkCompanyAdapter : RecyclerView.Adapter<BookmarkCompanyAdapter.BookmarkCompanyViewHolder>() {

    private val data: MutableList<Docs> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkCompanyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCompanyBinding.inflate(layoutInflater, parent, false)
        return BookmarkCompanyViewHolder(parent.context, binding)
    }

    override fun onBindViewHolder(holder: BookmarkCompanyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class BookmarkCompanyViewHolder(
        private val context: Context,
        private val binding: ItemCompanyBinding
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

    fun setItem(company: List<Docs>) {
        data.clear()
        data.addAll(company)
        notifyDataSetChanged()
    }
}

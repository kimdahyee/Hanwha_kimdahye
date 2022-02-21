package com.example.hanwha_kimdahye.ui.adapter.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hanwha_kimdahye.R
import com.example.hanwha_kimdahye.data.database.BookmarkDatabase
import com.example.hanwha_kimdahye.data.model.Docs
import com.example.hanwha_kimdahye.databinding.ItemNewsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsSearchAdapter :
    PagingDataAdapter<Docs, NewsSearchAdapter.NewsSearchViewHolder>(DiffUtilCallBack()) {

    private lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsSearchViewHolder =
        NewsSearchViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_news,
                parent,
                false
            ),
            parent.context
        )

    override fun onBindViewHolder(holder: NewsSearchViewHolder, position: Int) {
        getItem(position)?.let { docs ->
            holder.bind(docs)
            holder.itemView.setOnClickListener {
                itemClickListener.onClick(it, position, docs)
            }
        }
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int, docs: Docs)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    class NewsSearchViewHolder(
        private val binding: ItemNewsBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: Docs) {
            binding.news = news
            binding.imgImageUrl.clipToOutline = true
            var count: Int
            val db = BookmarkDatabase.getInstance(context)
            CoroutineScope(Dispatchers.IO).launch {
                count = db!!.bookmarkDao().initCheck(news.uid)
                if (count == 1) {
                    bookmarkedPage()
                } else {
                    notBookmarkedPage()
                }
            }
            binding.btnNewsBookmark.setOnClickListener { setBookmarkButtonClickEvent(news) }
        }

        private fun bookmarkedPage() {
            binding.btnNewsBookmark.isSelected = true
        }

        private fun notBookmarkedPage() {
            binding.btnNewsBookmark.isSelected = false
        }

        private fun setBookmarkButtonClickEvent(news: Docs) {
            if (getBookmarkButtonStatus()) {
                // 북마크가 이미 되어있는 상황
                val db = BookmarkDatabase.getInstance(context)
                CoroutineScope(Dispatchers.IO).launch {
                    db!!.bookmarkDao().delete(news.uid)
                }
                setBookmarkButtonStatus(false)
                return
            }

            val db = BookmarkDatabase.getInstance(context)
            CoroutineScope(Dispatchers.IO).launch {
                db!!.bookmarkDao().insert(news)
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

        private fun getBookmarkButtonStatus(): Boolean = binding.btnNewsBookmark.isSelected
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Docs>() {
        override fun areItemsTheSame(oldItem: Docs, newItem: Docs): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Docs, newItem: Docs): Boolean {
            return oldItem == newItem
        }
    }
}

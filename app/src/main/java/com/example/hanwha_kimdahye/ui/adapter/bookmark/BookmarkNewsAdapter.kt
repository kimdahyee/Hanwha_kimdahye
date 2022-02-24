package com.example.hanwha_kimdahye.ui.adapter.bookmark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hanwha_kimdahye.data.database.BookmarkDatabase
import com.example.hanwha_kimdahye.data.model.Docs
import com.example.hanwha_kimdahye.databinding.ItemNewsBinding
import com.example.hanwha_kimdahye.ui.viewmodel.BookmarkViewModel
import com.example.hanwha_kimdahye.util.DiffUtilCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created By kimdahyee
 * on 02월 20일, 2020
 */

class BookmarkNewsAdapter(val viewModel: BookmarkViewModel) : RecyclerView.Adapter<BookmarkNewsAdapter.BookmarkNewsViewHolder>() {

    private val data: MutableList<Docs> = mutableListOf()
    private lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkNewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(layoutInflater, parent, false)
        return BookmarkNewsViewHolder(parent.context, binding, viewModel)
    }

    override fun onBindViewHolder(holder: BookmarkNewsViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    class BookmarkNewsViewHolder(
        private val context: Context,
        private val binding: ItemNewsBinding,
        private val bookmarkViewModel: BookmarkViewModel
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
                bookmarkViewModel.getBookmarkNewsResult(context, "news")
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

    fun setItem(news: List<Docs>) {
        val diffUtilCallback = DiffUtilCallback(data, news)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        data.clear()
        data.addAll(news)
        diffResult.dispatchUpdatesTo(this)
    }
}

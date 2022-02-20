package com.example.hanwha_kimdahye.ui.adapter.bookmark

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hanwha_kimdahye.data.model.Bookmark
import com.example.hanwha_kimdahye.databinding.ItemNewsBinding

/**
 * Created By kimdahyee
 * on 02월 20일, 2020
 */

class BookmarkNewsAdapter : RecyclerView.Adapter<BookmarkNewsAdapter.BookmarkNewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkNewsViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BookmarkNewsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class BookmarkNewsViewHolder(
        private val binding: ItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bookmark: Bookmark) {
        }
    }
}

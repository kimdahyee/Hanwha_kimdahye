package com.example.hanwha_kimdahye.ui.adapter.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hanwha_kimdahye.data.model.Docs
import com.example.hanwha_kimdahye.databinding.ItemNewsBinding

/**
 * Created By kimdahyee
 * on 02월 20일, 2020
 */

class BookmarkNewsAdapter : RecyclerView.Adapter<BookmarkNewsAdapter.BookmarkNewsViewHolder>() {

    private val data: MutableList<Docs> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkNewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(layoutInflater, parent, false)
        return BookmarkNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkNewsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class BookmarkNewsViewHolder(
        private val binding: ItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: Docs) {
            binding.news = news
            binding.imgImageUrl.clipToOutline = true
        }
    }

    fun setItem(news: List<Docs>) {
        data.clear()
        data.addAll(news)
        notifyDataSetChanged()
    }
}

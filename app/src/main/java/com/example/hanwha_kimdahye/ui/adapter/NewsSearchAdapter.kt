package com.example.hanwha_kimdahye.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hanwha_kimdahye.R
import com.example.hanwha_kimdahye.data.model.Docs
import com.example.hanwha_kimdahye.databinding.ItemNewsBinding

class NewsSearchAdapter :
    PagingDataAdapter<Docs, NewsSearchAdapter.NewsSearchViewHolder>(SearchDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsSearchViewHolder =
        NewsSearchViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_news,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NewsSearchViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class NewsSearchViewHolder(
        private val binding: ItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: Docs) {
            binding.news = news
        }
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

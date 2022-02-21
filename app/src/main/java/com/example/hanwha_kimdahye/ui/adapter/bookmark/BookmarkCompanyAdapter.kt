package com.example.hanwha_kimdahye.ui.adapter.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hanwha_kimdahye.data.model.Docs
import com.example.hanwha_kimdahye.databinding.ItemCompanyBinding

/**
 * Created By kimdahyee
 * on 02월 20일, 2020
 */

class BookmarkCompanyAdapter : RecyclerView.Adapter<BookmarkCompanyAdapter.BookmarkCompanyViewHolder>() {

    private val data: MutableList<Docs> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkCompanyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCompanyBinding.inflate(layoutInflater, parent, false)
        return BookmarkCompanyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkCompanyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class BookmarkCompanyViewHolder(
        private val binding: ItemCompanyBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(company: Docs) {
            binding.company = company
        }
    }

    fun setItem(company: List<Docs>) {
        data.clear()
        data.addAll(company)
        notifyDataSetChanged()
    }
}

package com.example.hanwha_kimdahye.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hanwha_kimdahye.R
import com.example.hanwha_kimdahye.data.model.Docs
import com.example.hanwha_kimdahye.databinding.ItemCompanyBinding

class CompanySearchAdapter :
    PagingDataAdapter<Docs, CompanySearchAdapter.CompanySearchViewHolder>(SearchDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanySearchViewHolder =
        CompanySearchViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_company,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CompanySearchViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class CompanySearchViewHolder(
        private val binding: ItemCompanyBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(company: Docs) {
            binding.company = company
        }
    }

    class SearchDiffCallBack : DiffUtil.ItemCallback<Docs>() {
        override fun areItemsTheSame(oldItem: Docs, newItem: Docs): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Docs, newItem: Docs): Boolean {
            return oldItem == newItem
        }
    }
}

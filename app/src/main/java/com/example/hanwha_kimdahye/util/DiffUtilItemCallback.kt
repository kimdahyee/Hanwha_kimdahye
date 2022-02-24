package com.example.hanwha_kimdahye.util

import androidx.recyclerview.widget.DiffUtil
import com.example.hanwha_kimdahye.data.model.Docs

class DiffUtilItemCallback : DiffUtil.ItemCallback<Docs>() {
    override fun areItemsTheSame(oldItem: Docs, newItem: Docs): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: Docs, newItem: Docs): Boolean {
        return oldItem == newItem
    }
}

package com.example.hanwha_kimdahye.util

import androidx.recyclerview.widget.DiffUtil
import com.example.hanwha_kimdahye.data.model.Docs

class BookmarkDiffUtil(
    private val oldList: List<Docs>,
    private val newList: List<Docs>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].uid == newList[newItemPosition].uid

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}

package com.darkular.tickets.ui.chat

import androidx.recyclerview.widget.DiffUtil


class ChatDiffUtilCallback(
    private val oldList: List<MessageUi>,
    private val newList: List<MessageUi>,
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        newList[newItemPosition].same(oldList[oldItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        newList[newItemPosition] == oldList[oldItemPosition]
}
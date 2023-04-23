package com.darkular.tickets.ui.chats

import androidx.recyclerview.widget.DiffUtil


class ChatsDiffUtilCallback(
    private val oldList: List<ChatUi>,
    private val newList: List<ChatUi>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].same(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}
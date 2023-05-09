package com.darkular.tickets.presentation.chats.adapter

import androidx.recyclerview.widget.DiffUtil
import com.darkular.tickets.presentation.chats.ChatUi

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
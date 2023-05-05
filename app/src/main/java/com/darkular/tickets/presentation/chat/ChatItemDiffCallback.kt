package com.darkular.tickets.presentation.chat

import androidx.recyclerview.widget.DiffUtil


class ChatItemDiffCallback : DiffUtil.ItemCallback<MessageUi>() {

    override fun areItemsTheSame(oldItem: MessageUi, newItem: MessageUi) = newItem.same(oldItem)
    override fun areContentsTheSame(oldItem: MessageUi, newItem: MessageUi) = newItem.sameContent(oldItem)
}
package com.darkular.tickets.presentation.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.darkular.tickets.databinding.MyMessageLayoutBinding
import com.darkular.tickets.databinding.UserMessageLayoutBinding
import com.darkular.tickets.presentation.chat.MessageUi
import com.darkular.tickets.presentation.core.ClickListener
import com.darkular.tickets.presentation.core.ItemDiffCallback

class ChatAdapter(
    private val clickListener: ClickListener<MessageUi>,
    private val readMessage: ClickListener<MessageUi>
) : ListAdapter<MessageUi, ChatViewHolder>(ItemDiffCallback<MessageUi>()) {

    override fun getItemViewType(position: Int) = if (getItem(position).isMyMessage()) 1 else 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = if (viewType == 1)
        ChatViewHolder.MyMessageViewHolder(
            MyMessageLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), clickListener
        )
    else
        ChatViewHolder.UserMessageViewHolder(
            UserMessageLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), readMessage
        )

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) =
        holder.bind(getItem(position))
}
package com.darkular.tickets.presentation.chat.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.darkular.tickets.databinding.MyMessageLayoutBinding
import com.darkular.tickets.databinding.UserMessageLayoutBinding
import com.darkular.tickets.presentation.chat.MessageUi
import com.darkular.tickets.presentation.core.ClickListener

abstract class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    open fun bind(item: MessageUi) {}

    class MyMessageViewHolder(
        private val binding: MyMessageLayoutBinding,
        private val clickListener: ClickListener<MessageUi>
    ) : ChatViewHolder(binding.root) {

        override fun bind(item: MessageUi) {
            item.map(binding.messageTextView, binding.progressBar, binding.iconView)
            binding.iconView.setOnClickListener { item.click(clickListener) }
        }
    }

    class UserMessageViewHolder(
        private val binding: UserMessageLayoutBinding,
        private val readMessage: ClickListener<MessageUi>
    ) : ChatViewHolder(binding.root) {
        override fun bind(item: MessageUi) {
            item.map(binding.messageTextView, binding.userAvatarImageView)
            readMessage.click(item)
        }
    }
}
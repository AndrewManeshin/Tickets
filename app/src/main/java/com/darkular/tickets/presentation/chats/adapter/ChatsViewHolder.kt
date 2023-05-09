package com.darkular.tickets.presentation.chats.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.darkular.tickets.databinding.ChatLayoutBinding
import com.darkular.tickets.databinding.GroupLayoutBinding
import com.darkular.tickets.presentation.chats.ChatUi
import com.darkular.tickets.presentation.core.ClickListener

abstract class ChatsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(item: ChatUi) {}

    class Chat(
        private val binding: ChatLayoutBinding,
        private val clickListener: ClickListener<ChatUi>
    ) : ChatsViewHolder(binding.root) {

        override fun bind(item: ChatUi) = with(binding) {
            item.mapChat(
                userNameTextView,
                avatarImageView,
                messageTextView,
                fromMeTextView,
                unreadMessagesCountTextView
            )
            itemView.setOnClickListener { clickListener.click(item) }
        }
    }

    class Group(
        private val binding: GroupLayoutBinding,
        private val clickListener: ClickListener<ChatUi>
    ) : ChatsViewHolder(binding.root) {

        override fun bind(item: ChatUi) = with(binding) {
            item.mapGroup(
                groupNameTextView,
                groupAvatarImageView,
                messageTextView,
                lastMessageFromUserNameTextView,
                unreadMessagesCountTextView
            )
            itemView.setOnClickListener { clickListener.click(item) }
        }
    }
}
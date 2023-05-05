package com.darkular.tickets.presentation.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.darkular.tickets.core.Abstract
import com.darkular.tickets.databinding.ChatLayoutBinding
import com.darkular.tickets.databinding.GroupLayoutBinding
import com.darkular.tickets.presentation.core.ClickListener

class ChatsAdapter(private val clickListener: ClickListener<ChatUi>) :
    RecyclerView.Adapter<ChatsViewHolder>(),
    Abstract.Mapper.Data<List<ChatUi>, Unit> {

    private val list = ArrayList<ChatUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        0 -> ChatsViewHolder.Chat(
            ChatLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickListener
        )
        1 -> ChatsViewHolder.Group(
            GroupLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickListener
        )
        else -> throw IllegalStateException("unknown view type")
    }

    override fun getItemViewType(position: Int) =
        when (list[position]) {
            is ChatUi.Chat -> 0
            is ChatUi.Group -> 1
            else -> throw IllegalStateException("unknown view type")
        }


    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount() = list.size

    override fun map(data: List<ChatUi>) {
        val result = DiffUtil.calculateDiff(ChatsDiffUtilCallback(list, data))
        list.clear()
        list.addAll(data)
        result.dispatchUpdatesTo(this)
    }
}

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
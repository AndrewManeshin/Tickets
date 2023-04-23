package com.darkular.tickets.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.darkular.tickets.core.Abstract
import com.darkular.tickets.databinding.ChatLayoutBinding
import com.darkular.tickets.ui.core.ClickListener


class ChatsAdapter(private val clickListener: ClickListener<ChatUi>) :
    RecyclerView.Adapter<ChatsViewHolder>(),
    Abstract.Mapper.Data<List<ChatUi>, Unit> {

    private val list = ArrayList<ChatUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChatsViewHolder.Base(
        ChatLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        clickListener
    )

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

    class Base(
        private val binding: ChatLayoutBinding,
        private val clickListener: ClickListener<ChatUi>
    ) : ChatsViewHolder(binding.root) {
        override fun bind(item: ChatUi) = with(binding) {
            item.map(
                userNameTextView,
                avatarImageView,
                messageTextView,
                fromMeTextView,
                unreadMessagesCountTextView
            )
            itemView.setOnClickListener { clickListener.click(item) }
        }
    }
}
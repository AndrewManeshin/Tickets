package com.darkular.tickets.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.darkular.tickets.core.Abstract
import com.darkular.tickets.databinding.MyMessageLayoutBinding
import com.darkular.tickets.databinding.UserMessageLayoutBinding
import com.darkular.tickets.ui.core.ClickListener


class ChatAdapter(
    private val clickListener: ClickListener<MessageUi>,
    private val readMessage: ClickListener<MessageUi>
) : RecyclerView.Adapter<ChatViewHolder>(),
    Abstract.Mapper.Data<List<MessageUi>, Unit> {

    private val messages = ArrayList<MessageUi>()

    override fun getItemViewType(position: Int) = if (messages[position].isMyMessage()) 1 else 0

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
        holder.bind(messages[position])

    override fun getItemCount() = messages.size

    override fun map(data: List<MessageUi>) {
        val result = DiffUtil.calculateDiff(ChatDiffUtilCallback(messages, data))
        messages.clear()
        messages.addAll(data)
        result.dispatchUpdatesTo(this)
    }
}

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
    ) :
        ChatViewHolder(binding.root) {
        override fun bind(item: MessageUi) {
            item.map(binding.messageTextView)
            readMessage.click(item)
        }
    }
}
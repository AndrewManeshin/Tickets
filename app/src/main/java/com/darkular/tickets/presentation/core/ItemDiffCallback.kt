package com.darkular.tickets.presentation.core

import androidx.recyclerview.widget.DiffUtil
import com.darkular.tickets.core.Comparable

class ItemDiffCallback<T: Comparable<T>> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.same(newItem)
    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem.sameContent(newItem)
}
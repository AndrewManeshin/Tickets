package com.darkular.tickets.presentation.group.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darkular.tickets.core.Abstract
import com.darkular.tickets.databinding.MyGroupLayoutBinding


class MyGroupsAdapter : RecyclerView.Adapter<MyGroupViewHolder>(),
    Abstract.Mapper.Data<List<String>, Unit> {

    private val list = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyGroupViewHolder(
        MyGroupLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: MyGroupViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount() = list.size

    override fun map(data: List<String>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}

class MyGroupViewHolder(private val binding: MyGroupLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(name: String) = binding.myGroupNameTextView.map(name)
}
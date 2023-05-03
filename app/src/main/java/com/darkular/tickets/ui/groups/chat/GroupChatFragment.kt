package com.darkular.tickets.ui.groups.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darkular.tickets.R
import com.darkular.tickets.databinding.FragmentChatBinding
import com.darkular.tickets.sl.core.Feature
import com.darkular.tickets.ui.chat.ChatAdapter
import com.darkular.tickets.ui.chat.MessageUi
import com.darkular.tickets.ui.main.BaseFragment
import com.darkular.tickets.ui.core.ClickListener

class GroupFragment : BaseFragment<GroupViewModel>() {

    override fun viewModelClass() = GroupViewModel::class.java
    override val titleResId = R.string.title_group
    override fun name() = Feature.GROUP.name
    override fun showBottomNavigation() = false

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sendMessageButton.setOnClickListener {
            viewModel.send(binding.messageEditText.text.toString())
            binding.messageEditText.text?.clear()
        }
        val adapter = ChatAdapter(Retry(), ReadMessage()) // TODO change to group adapter
        binding.recyclerView.adapter = adapter
        viewModel.observe(this) {
            adapter.map(it)
            binding.recyclerView.scrollToPosition(it.size - 1)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class Retry : ClickListener<MessageUi> {
        override fun click(item: MessageUi) { item.map(viewModel) }
    }

    private inner class ReadMessage : ClickListener<MessageUi> {
        override fun click(item: MessageUi) { item.read(viewModel)}
    }
}
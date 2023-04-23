package com.darkular.tickets.ui.groups.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darkular.tickets.R
import com.darkular.tickets.databinding.FragmentCreateGroupBinding
import com.darkular.tickets.sl.core.Feature
import com.darkular.tickets.ui.main.BaseFragment


class CreateGroupFragment : BaseFragment<CreateGroupViewModel>() {
    override fun viewModelClass() = CreateGroupViewModel::class.java
    override val titleResId = R.string.create_group
    override fun name() = Feature.CREATE_GROUP.name
    override fun showBottomNavigation() = false

    private var _binding: FragmentCreateGroupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createGroupButton.setOnClickListener {
            viewModel.createGroup(binding.groupNameEditText.text.toString())
            binding.groupNameEditText.text?.clear()
        }
        val adapter = MyGroupsAdapter()
        binding.myGroupsRecyclerView.adapter = adapter
        viewModel.observe(this) { it.map(adapter) }
        viewModel.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
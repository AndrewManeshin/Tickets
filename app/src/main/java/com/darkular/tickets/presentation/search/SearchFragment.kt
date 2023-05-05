package com.darkular.tickets.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darkular.tickets.R
import com.darkular.tickets.databinding.FragmentSearchBinding
import com.darkular.tickets.sl.core.Feature
import com.darkular.tickets.presentation.core.ClickListener
import com.darkular.tickets.presentation.main.BaseFragment

class SearchFragment : BaseFragment<SearchViewModel>() {
    override fun viewModelClass() = SearchViewModel::class.java
    override val titleResId = R.string.title_search
    override fun name() = Feature.SEARCH.name

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchView.setOnQueryTextListener(SimpleQueryListener(viewModel))
        val adapter = SearchAdapter(OpenChat())
        binding.recyclerView.adapter = adapter
        viewModel.observe(this) { it.map(adapter) }
        viewModel.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.searchView.setOnQueryTextListener(null)
        _binding = null
    }

    private inner class OpenChat : ClickListener<SearchResultUi> {
        override fun click(item: SearchResultUi) = item.chat(viewModel)
    }
}
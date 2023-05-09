package com.darkular.tickets.presentation.group.create.films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darkular.tickets.R
import com.darkular.tickets.databinding.FragmentFilmsBinding
import com.darkular.tickets.presentation.core.ClickListener
import com.darkular.tickets.presentation.group.create.films.adapter.FilmsAdapter
import com.darkular.tickets.presentation.main.BaseFragment
import com.darkular.tickets.sl.core.Feature

class FilmsFragment : BaseFragment<FilmsViewModel>(), FilmUi.OnSelected {

    override val titleResId = R.string.title_films
    override fun name() = Feature.FILMS.name
    override fun viewModelClass() = FilmsViewModel::class.java

    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FilmsAdapter(OnTryAgain(), this)
        binding.recyclerView.adapter = adapter
        if (savedInstanceState == null) viewModel.fetchFilms()
        viewModel.observe(this) { adapter.submitList(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun OnSelected(filmId: String, posterUrl: String) {
        viewModel.createGroupWithFilm(filmId, posterUrl)
    }

    private inner class OnTryAgain : ClickListener<Unit> {
        override fun click(item: Unit) {
            viewModel.fetchFilms()
        }
    }
}
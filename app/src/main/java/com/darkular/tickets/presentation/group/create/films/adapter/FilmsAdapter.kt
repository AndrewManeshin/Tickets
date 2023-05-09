package com.darkular.tickets.presentation.group.create.films.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.darkular.tickets.databinding.FailLayoutBinding
import com.darkular.tickets.databinding.FilmItemLayoutBinding
import com.darkular.tickets.databinding.NoResultsLayoutBinding
import com.darkular.tickets.databinding.ProgressLayoutBinding
import com.darkular.tickets.presentation.core.ClickListener
import com.darkular.tickets.presentation.core.ItemDiffCallback
import com.darkular.tickets.presentation.group.create.films.FilmUi

class FilmsAdapter(
    private var onTryAgain: ClickListener<Unit>,
    private val onSelected: FilmUi.OnSelected
) : ListAdapter<FilmUi, FilmUiTypeViewHolder>(ItemDiffCallback<FilmUi>()) {

    override fun getItemViewType(position: Int) = getItem(position).type().ordinal

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = when (viewType) {
        FilmUi.Type.EMPTY.ordinal -> FilmUiTypeViewHolder.Empty(
            NoResultsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        FilmUi.Type.FAIL.ordinal -> FilmUiTypeViewHolder.Fail(
            onTryAgain,
            FailLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        FilmUi.Type.PROGRESS.ordinal -> FilmUiTypeViewHolder.Progress(
            ProgressLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        FilmUi.Type.BASE.ordinal -> FilmUiTypeViewHolder.Base(
            FilmItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onSelected
        )
        else -> throw java.lang.IllegalStateException("unknown viewType $viewType")
    }

    override fun onBindViewHolder(holder: FilmUiTypeViewHolder, position: Int) =
        holder.bind(getItem(position))
}
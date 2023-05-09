package com.darkular.tickets.presentation.group.create.films.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.darkular.tickets.databinding.FailLayoutBinding
import com.darkular.tickets.databinding.FilmItemLayoutBinding
import com.darkular.tickets.databinding.NoResultsLayoutBinding
import com.darkular.tickets.databinding.ProgressLayoutBinding
import com.darkular.tickets.presentation.core.ClickListener
import com.darkular.tickets.presentation.group.create.films.FilmUi
import com.darkular.tickets.presentation.group.create.films.FilmUiMapper

abstract class FilmUiTypeViewHolder(view: View) : RecyclerView.ViewHolder(view), FilmUiMapper {

    open fun bind(item: FilmUi) = item.map(this)

    class Empty(binding: NoResultsLayoutBinding) : FilmUiTypeViewHolder(binding.root)
    class Progress(binding: ProgressLayoutBinding) : FilmUiTypeViewHolder(binding.root)

    class Fail(
        private val onTryAgain: ClickListener<Unit>,
        private val binding: FailLayoutBinding
    ) : FilmUiTypeViewHolder(binding.root) {

        override fun bind(item: FilmUi) {
            item.map(this)
            binding.tryAgainButton.setOnClickListener {
                onTryAgain.click(Unit)
            }
        }

        override fun mapMessage(message: String) {
            binding.failMessageTextView.text = message
        }
    }

    class Base(private val binding: FilmItemLayoutBinding, private val onSelected: FilmUi.OnSelected) : FilmUiTypeViewHolder(binding.root) {

        override fun bind(item: FilmUi) {
            super.bind(item)
            binding.root.setOnClickListener {
                item.click(onSelected)
            }
        }

        override fun mapBase(name: String, rating: String, year: String, posterUrlPreview: String) {
            binding.filmNameTextView.text = name
            binding.filmRatingTextView.text = rating
            binding.filmYearTextView.text = year
            binding.filmPosterImageView.load(posterUrlPreview, isCircle = false)
        }
    }
}
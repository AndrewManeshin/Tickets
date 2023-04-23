package com.darkular.tickets.ui.search

import androidx.appcompat.widget.SearchView


class SimpleQueryListener(private val search: Search) : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?) = find(query)
    override fun onQueryTextChange(newText: String?) = find(newText)

    private fun find(query: String?): Boolean {
        search.search(query.orEmpty().trim())
        return !query.isNullOrEmpty()
    }
}
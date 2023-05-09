package com.darkular.tickets.presentation.group.create.films

import androidx.lifecycle.viewModelScope
import com.darkular.tickets.R
import com.darkular.tickets.domain.group.CreateGroupInteractor
import com.darkular.tickets.presentation.core.BaseViewModel
import com.darkular.tickets.presentation.core.Communication
import com.darkular.tickets.presentation.main.NavigationCommunication
import com.darkular.tickets.presentation.main.NavigationUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface FilmsCommunication : Communication<List<FilmUi>> {
    class Base : Communication.Base<List<FilmUi>>(), FilmsCommunication
}

class FilmsViewModel(
    communication: FilmsCommunication,
    private val interactor: CreateGroupInteractor,
    private val navigation: NavigationCommunication
) : BaseViewModel<FilmsCommunication, List<FilmUi>>(communication) {

    fun fetchFilms() {
        FilmsUi(listOf(FilmUi.Progress)).map(communication)
        viewModelScope.launch(Dispatchers.IO) {
            val result = interactor.fetchFilms()
            withContext(Dispatchers.Main) {
                result.map(communication)
            }
        }
    }

    fun createGroupWithFilm(filmId: String, photoUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.createGroup(filmId, photoUrl)
        }
        navigation.map(NavigationUi.SecondLevel(R.id.create_group_screen))
    }
}
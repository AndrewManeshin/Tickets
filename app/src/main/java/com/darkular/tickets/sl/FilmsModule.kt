package com.darkular.tickets.sl

import com.darkular.tickets.data.films.FilmsRepository
import com.darkular.tickets.data.group.create.CreateGroupRepository
import com.darkular.tickets.data.group.create.GroupNameContainer
import com.darkular.tickets.domain.group.CreateGroupInteractor
import com.darkular.tickets.presentation.group.create.films.FilmsCommunication
import com.darkular.tickets.presentation.group.create.films.FilmsViewModel
import com.darkular.tickets.sl.core.BaseModule
import com.darkular.tickets.sl.core.CoreModule

class FilmsModule(private val coreModule: CoreModule) : BaseModule<FilmsViewModel> {
    override fun viewModel() = FilmsViewModel(
        FilmsCommunication.Base(),
        CreateGroupInteractor.Base(
            CreateGroupRepository.Base(
                coreModule.firebaseDatabaseProvider(),
                GroupNameContainer.Base(coreModule.provideSharedPreferences())
            ),
            FilmsRepository.Base(coreModule.firebaseDatabaseProvider())
        ),
        coreModule.navigationCommunication()
    )
}
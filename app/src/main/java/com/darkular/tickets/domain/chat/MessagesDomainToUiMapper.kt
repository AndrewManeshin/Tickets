package com.darkular.tickets.domain.chat

import com.darkular.tickets.core.Abstract


interface MessagesDomainToUiMapper<T> : Abstract.Mapper.DomainToUi<List<MessageDomain>, T>
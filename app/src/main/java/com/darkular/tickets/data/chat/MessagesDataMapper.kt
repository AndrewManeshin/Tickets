package com.darkular.tickets.data.chat

import com.darkular.tickets.core.Abstract
import com.darkular.tickets.domain.chat.MessageDomain
import com.darkular.tickets.domain.chat.MessagesDomain

interface MessagesDataMapper<T> : Abstract.Mapper.DataToDomain<List<Pair<String, MessageData>>, T>

class BaseMessagesDataMapper : MessagesDataMapper<MessagesDomain> {
    override fun map(data: List<Pair<String, MessageData>>) =
        MessagesDomain.Success(data.map { (id, data) ->
            if (data.messageIsMine())
                MessageDomain.MyMessageDomain(id, data.messageBody(), data.wasReadByUser())
            else
                MessageDomain.UserMessageDomain(id, data.messageBody(), data.avatarUri() , data.wasReadByUser())
        })

    override fun map(e: Exception): MessagesDomain {
        return MessagesDomain.Fail(e.message ?: "error")
    }
}
package com.darkular.tickets.ui.chats

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darkular.tickets.domain.chats.*
import com.darkular.tickets.ui.main.NavigationCommunication
import com.darkular.tickets.ui.main.NavigationUi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.*
import org.junit.Test

/**
 * Test for [ChatsViewModel]
 */
class ChatsViewModelTest {

    @Test
    fun test_one_chat() = runBlocking {
        val communication = TestChatsCommunication()
        val navigation = TestNavigation()
        val interactor = TestInteractor()
        val dispatchers = TestCoroutineDispatcher()
        val viewModel = ChatsViewModel(
            communication,
            navigation,
            interactor,
            ChatDomainMapper.Base(),
            UserChatDomainMapper.Base(),
            dispatchers,
            dispatchers
        )
        viewModel.startGettingUpdates()
        val actual = communication.list
        val expected = listOf<ChatUi>(
            ChatUi.Base(
                UserChatUi.Base(
                    "otherUserId",
                    "nameOfotherUserId",
                    "urlOfotherUserId"
                ),
                "hi",
                false,
                0
            )
        )
        assertEquals(expected, actual)
    }

    private inner class TestChatsCommunication : ChatsCommunication {
        var list: List<ChatUi> = emptyList()
        override fun observe(owner: LifecycleOwner, observer: Observer<List<ChatUi>>) = Unit

        override fun map(data: List<ChatUi>) {
            list = data
        }
    }

    private inner class TestNavigation : NavigationCommunication {
        lateinit var navigationUi: NavigationUi
        override fun observe(owner: LifecycleOwner, observer: Observer<NavigationUi>) = Unit

        override fun map(data: NavigationUi) {
            navigationUi = data
        }
    }

    private inner class TestInteractor : ChatsInteractor {
        var stringSaved = ""
        override fun stopGettingUpdates() = Unit

        override fun startGettingUpdates(callback: ChatsRealtimeUpdateCallback) {
            callback.updateChats(listOf(ChatDomain.LastMessageFromMe("otherUserId", "hi", 0)))
        }

        override suspend fun userInfo(userId: String): UserChatDomain {
            return UserChatDomain.Base(userId, "nameOf$userId", "urlOf$userId")
        }

        override fun save(data: String) {
            stringSaved = data
        }
    }
}
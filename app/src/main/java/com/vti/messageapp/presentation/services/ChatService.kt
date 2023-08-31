package com.vti.messageapp.presentation.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.vti.messageapp.domain.repositories.AuthRepository
import com.vti.messageapp.domain.repositories.UserRepository
import com.vti.messageapp.domain.usecases.CreateConversationParams
import com.vti.messageapp.domain.usecases.GetConversationUseCase
import com.vti.messageapp.domain.usecases.ReceiveMessageParam
import com.vti.messageapp.domain.usecases.ReceiveMessageUseCase
import com.vti.messageapp.domain.usecases.SendMessageParam
import com.vti.messageapp.domain.usecases.SendMessageUseCase
import com.vti.messageapp.shared.Constants.Companion.CHAT_DOMAIN
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jxmpp.jid.impl.JidCreate
import javax.inject.Inject


@AndroidEntryPoint
class ChatService : Service() {
    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var getConversationUseCase: GetConversationUseCase

    @Inject
    lateinit var sendMessageUseCase: SendMessageUseCase

    @Inject
    lateinit var receiveMessageUseCase: ReceiveMessageUseCase

    private val binder = LocalBinder()

    @OptIn(DelicateCoroutinesApi::class)
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->

        val context = this

        GlobalScope.launch(Dispatchers.Main){

            loading.value = false
            Toast.makeText(
                context,
                exception.message,
                Toast.LENGTH_SHORT
            ).show()
            job = Job()
        }

    }

    private var job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job + exceptionHandler)

    private var connection: AbstractXMPPConnection? = null
    private lateinit var chatManager: ChatManager

    val loading: MutableState<Boolean?> = mutableStateOf(null)

    val loginSuccess: MutableState<Boolean> = mutableStateOf(false)

    inner class LocalBinder : Binder() {
        fun getService(): ChatService = this@ChatService
    }


    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        val config = XMPPTCPConnectionConfiguration.builder()
            .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
            .setPort(5222)
            .setXmppDomain("uatchat2.waafi.com")
            .build()
        connection = XMPPTCPConnection(config)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        scope.launch(exceptionHandler) {
            val isAuthenticated = intent.getBooleanExtra("isAuthenticated", false)
            if (isAuthenticated) {
                loginSuccess.value = false
                val authInfo = authRepository.getAuthInfo()!!
                login(authInfo.username, authInfo.password)
            }
        }

        return START_STICKY

    }

    fun login(username: String, password: String) {
        loading.value = true
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            if (connection?.isConnected != true) {
                connection?.connect()
            }
            connection?.login(username, password)
            chatManager = ChatManager.getInstanceFor(connection)
            chatManager.addIncomingListener { from, message, _ ->
                scope.launch {
                    val phone = from.toString().split("@").first()
                    val user = userRepository.getUserByPhone(phone)
                    getConversationUseCase.invoke(CreateConversationParams(user))
                    receiveMessageUseCase.invoke(ReceiveMessageParam(message.body, user.id))
                }
            }

            withContext(Dispatchers.Main) {
                authRepository.login(username, password)
                loginSuccess.value = connection?.isAuthenticated ?: false
                loading.value = false
            }
        }
    }

    fun logout() {
        loginSuccess.value = false
        connection?.disconnect()
    }

    fun sendMessage(message: String, toObj: Long) {
        scope.launch {
            val me = userRepository.getMyUserInfo()

            if (me.id != toObj) {
                val user = userRepository.getUserById(toObj)
                val jid = JidCreate.entityBareFrom("${user.phone}@${CHAT_DOMAIN}")
                val chat: Chat = chatManager.chatWith(jid)
                chat.send(message)
                sendMessageUseCase.invoke(SendMessageParam(message, toObj))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loginSuccess.value = false
        connection?.disconnect()
        job.cancel()
    }
}
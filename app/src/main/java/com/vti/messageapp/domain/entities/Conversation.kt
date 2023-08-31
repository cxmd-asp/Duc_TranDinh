package com.vti.messageapp.domain.entities

data class Conversation(
    val id: Long,
    val title: String = "",
    val ownerId: Long,
    val image: String = "",
    val participants: List<User>,
//    val message: List<MessageRegister> = listOf(),
    val lastMessage: MessageRegister? = null
)
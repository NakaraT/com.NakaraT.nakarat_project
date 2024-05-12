package com.NakaraT.cache

import com.NakaraT.register.RegisterRecieveRemote

data class TokenCache(
    val login: String,
    val token: String
)

object InMemoryCache {
    val userList: MutableList<RegisterRecieveRemote> = mutableListOf()
    val token: MutableList<TokenCache> = mutableListOf()
}
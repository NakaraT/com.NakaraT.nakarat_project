package com.NakaraT.register

import com.NakaraT.cache.InMemoryCache
import com.NakaraT.cache.TokenCache
import com.NakaraT.data.User
import com.NakaraT.data.getDatabaseBuilder
import com.NakaraT.data.getRoomDatabase
import com.NakaraT.utils.checkForValid
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.coroutines.EmptyCoroutineContext

fun Application.configureRegisterRouting(){
    routing {
        post("/register") {
            val recieve = call.receive<RegisterRecieveRemote>()
            if(checkForValid(recieve.email)){
                call.respond(HttpStatusCode.BadRequest, "Email is not valid")
            }

            if (InMemoryCache.userList.map { it.login }.contains(recieve.login)){
                call.respond(HttpStatusCode.Conflict, "User already exists")
            }

            val token = UUID.randomUUID().toString()
            val db = getRoomDatabase(getDatabaseBuilder())
            val userDao = db.getDao()
            CoroutineScope(EmptyCoroutineContext).launch(Dispatchers.IO) {
                userDao.insert(User(recieve.login,recieve.email,recieve.password,token))
                println("Inserted into DB new user: ${userDao.getItem(recieve.login)}")
            }
            InMemoryCache.token.add(TokenCache(login = recieve.login, token = token))
            call.respond(RegisterResponseRemote(token = token))
        }
    }
}
package com.hajbo.routes

import com.hajbo.dao.User
import io.ktor.application.call
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.authentication
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get

fun Route.index() {

    get("/") {
        val msg = "Hello World!"
        call.respond(msg)
    }

}
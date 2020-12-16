package com.hajbo.routes

import com.hajbo.auth.JwtService
import com.hajbo.database.SpreadService
import com.hajbo.database.UserService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*

fun Route.spreads(
        spreadService: SpreadService
) {

    get("spreads") {
        val spreads = spreadService.getAllSpreads()
        call.respond(spreads)
    }

    get("spreads/{uuid}") {
        val spread = spreadService.getSpreadDto(UUID.fromString(call.parameters["uuid"]))
        call.respond(spread)
    }
}
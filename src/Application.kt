package com.hajbo

import com.fasterxml.jackson.databind.SerializationFeature
import com.hajbo.auth.JwtService
import com.hajbo.database.*
import com.hajbo.routes.auth
import com.hajbo.routes.index
import com.hajbo.routes.spreads
import com.hajbo.routes.users
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.auth.*
import io.ktor.auth.jwt.jwt
import io.ktor.jackson.*
import io.ktor.features.*
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)



@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    DatabaseFactory.init()
    seedDatabase()

    val userService = UserService()
    val grandmaService = GrandmaService()
    val spreadService = SpreadService()
    val jwtService = JwtService("my-super-secret-for-jwt")


    install(CallLogging)
    install(StatusPages) {
        exception<InvalidCredentialsException> { exception ->
            call.respond(HttpStatusCode.Unauthorized, mapOf("OK" to false, "error" to (exception.message ?: "")))
        }
    }
    install(Authentication) {
        jwt {
            verifier(jwtService.verifier)
            validate {
                val uuid = it.payload.getClaim("uuid").asString()
                val user = userService.getUser(UUID.fromString(uuid))

                return@validate UserIdPrincipal(uuid)
            }
        }
    }

    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    install(Routing) {
        index()
        auth(userService, jwtService)
        authenticate {
            users(userService, spreadService)
            spreads(spreadService)
        }

    }
}


class InvalidCredentialsException(message: String) : RuntimeException(message)

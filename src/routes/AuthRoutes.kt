package com.hajbo.routes


import com.hajbo.auth.JwtService
import com.hajbo.auth.hashFunction
import com.hajbo.database.UserService
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.post

fun Route.auth(
    db: UserService,
    jwtService: JwtService
) {

    post("register") {
        val signupParameters = call.receive<Parameters>()
        val password = signupParameters["password"]
            ?: return@post call.respond(
                HttpStatusCode.Unauthorized, "Missing Fields")
        val username = signupParameters["username"]
            ?: return@post call.respond(
                HttpStatusCode.Unauthorized, "Missing Fields")
        val email = signupParameters["email"]
            ?: return@post call.respond(
                HttpStatusCode.Unauthorized, "Missing Fields")
        val address = signupParameters["address"]
            ?: return@post call.respond(
                HttpStatusCode.Unauthorized, "Missing Fields")

        val passwordHash = hashFunction(password)

        try {
            val newUser = db.addUser(username, passwordHash, email, address)
            newUser?.uuid?.let {
                call.respondText(
//                    jwtService.generateToken(newUser),
                    jwtService.sign(newUser.uuid.toString()),
                    status = HttpStatusCode.Created
                )
            }
        } catch (e: Throwable) {
            application.log.error("Failed to register user", e)
            call.respond(HttpStatusCode.BadRequest, "Problems creating User")
        }
    }

    post("authenticate") {
        val signinParameters = call.receive<Parameters>()
        val password = signinParameters["password"]
            ?: return@post call.respond(
                HttpStatusCode.Unauthorized, "Missing Fields")
        val username = signinParameters["username"]
            ?: return@post call.respond(
                HttpStatusCode.Unauthorized, "Missing Fields")

        val passwordHash = hashFunction(password)

        try {
            val user = db.getUserByName(username)
            if (user.username == username && user.passwordHash == passwordHash) {
                user.uuid.let {
                    call.respondText(
                        jwtService.sign(user.username),
                        status = HttpStatusCode.Created
                    )
                }
            } else {
                error("Invalid credentials")
            }

        } catch (e: Throwable) {
            application.log.error("Failed to register user", e)
            call.respond(HttpStatusCode.BadRequest, "Problems creating User")
        }
    }

}
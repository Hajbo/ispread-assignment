package com.hajbo.routes

import com.hajbo.auth.JwtService
import com.hajbo.database.SpreadService
import com.hajbo.database.UserService
import io.ktor.application.call
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.authentication
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.respond
import io.ktor.routing.*
import java.util.*


fun Route.users(
        userService: UserService,
        spreadService: SpreadService
) {

    get("users") {
        val users = userService.getAllUsers()
        call.respond(users)
    }

    get("users/me") {
        val principal: UserIdPrincipal = call.authentication.principal<UserIdPrincipal>()!!
        val user = userService.getUserDto(UUID.fromString(principal.name))
        call.respond(user)
    }

    get("users/cart") {
        val principal: UserIdPrincipal = call.authentication.principal<UserIdPrincipal>()!!
        val user = userService.getUserDto(UUID.fromString(principal.name))
        call.respond(user.cart)
    }

    post("users/cart") {
        val principal: UserIdPrincipal = call.authentication.principal<UserIdPrincipal>()!!
        val user = userService.getUser(UUID.fromString(principal.name))
        val requestBody = call.receive<Parameters>()
        val spread = spreadService.getSpread(UUID.fromString(requestBody["spreadId"]))
        val quantity = if(requestBody["quantity"] != null) requestBody["quantity"]!!.toInt() else 1
        userService.addSpreadToCart(user, spread, quantity)
        call.respond(userService.getUserDto(UUID.fromString(principal.name)).cart)
    }

    delete("users/cart") {
        val principal: UserIdPrincipal = call.authentication.principal<UserIdPrincipal>()!!
        val user = userService.getUser(UUID.fromString(principal.name))
        val requestBody = call.receive<Parameters>()
        val spread = spreadService.getSpread(UUID.fromString(requestBody["spreadId"]))
        userService.deleteSpreadFromCart(user, spread)
        call.respond(userService.getUserDto(UUID.fromString(principal.name)).cart)
    }

    get("users/wishlist") {
        val principal: UserIdPrincipal = call.authentication.principal<UserIdPrincipal>()!!
        val user = userService.getUserDto(UUID.fromString(principal.name))
        call.respond(user.wishList)
    }

    post("users/wishlist") {
        val principal: UserIdPrincipal = call.authentication.principal<UserIdPrincipal>()!!
        val user = userService.getUser(UUID.fromString(principal.name))
        val requestBody = call.receive<Parameters>()
        val spread = spreadService.getSpread(UUID.fromString(requestBody["spreadId"]))
        userService.addSpreadToWishlist(user, spread)
        call.respond(userService.getUserDto(UUID.fromString(principal.name)).wishList)
    }

    delete("users/wishlist") {
        val principal: UserIdPrincipal = call.authentication.principal<UserIdPrincipal>()!!
        val user = userService.getUser(UUID.fromString(principal.name))
        val requestBody = call.receive<Parameters>()
        val spread = spreadService.getSpread(UUID.fromString(requestBody["spreadId"]))
        userService.deleteSpreadFromWishlist(user, spread)
        call.respond(userService.getUserDto(UUID.fromString(principal.name)).wishList)
    }

    get("users/cart/checkout") {
        val principal: UserIdPrincipal = call.authentication.principal<UserIdPrincipal>()!!
        val user = userService.getUser(UUID.fromString(principal.name))
        userService.checkoutCart(user, spreadService)
        call.respond(mapOf("message" to "Thank you for your order, we'll ship it soon!"))
    }
}
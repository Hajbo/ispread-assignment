package com.hajbo.database

import com.hajbo.dao.Grandma
import com.hajbo.dao.Spread
import com.hajbo.dao.User
import com.hajbo.dto.GrandmaDto
import com.hajbo.dto.SpreadDto
import com.hajbo.dto.UserDto
import com.hajbo.dto.UserSpreadDto

fun toUserDto(user: User): UserDto =
    UserDto(
        uuid = user.uuid,
        username = user.username,
        email = user.email,
        address = user.address,
        cart = user.cart.map { toUserSpreadDto(it.spread, it.quantity) },
        wishList =  user.wishList.map { toUserSpreadDto(it.spread, it.quantity) }
    )

fun toSpreadDto(spread: Spread) : SpreadDto =
    SpreadDto(
        uuid = spread.uuid,
        name = spread.name,
        type = spread.spreadType,
        remaining = spread.remaining,
        grandma = spread.grandma.uuid
    )

fun toUserSpreadDto(spread: Spread, quantity: Int) : UserSpreadDto =
    UserSpreadDto(
        uuid = spread.uuid,
        name = spread.name,
        type = spread.spreadType,
        quantity = quantity,
        remaining = spread.remaining,
        grandma = spread.grandma.uuid
    )

fun toGrandmaDto(grandma: Grandma) : GrandmaDto =
    GrandmaDto(
        uuid = grandma.uuid,
        username = grandma.username,
        availableSpreads = grandma.availableSpreads.map { toSpreadDto(it) }
    )


fun sendEmail(user: User, message: String) {
    val email = mapOf("email" to user.email, "message" to message)
    print("I'll need an SMPT server for it or some external API calls")
}
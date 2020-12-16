package com.hajbo.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable


object Users: IntIdTable() {
    val uuid = uuid("uuid").uniqueIndex()
    val username = varchar("username", 50)
    val email = varchar("email", 100)
    val passwordHash = varchar("passwordHash", 50)
    val address = varchar("address", 200)
}

class User(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var uuid by Users.uuid
    var username by Users.username
    var email by Users.email
    var passwordHash by Users.passwordHash
    var address by Users.address

    val cart by UserCartItem referrersOn UserCartItems.user
    val wishList by UserWishlistItem referrersOn UserWishlistItems.user
}
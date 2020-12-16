package com.hajbo.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object UserWishlistItems: IntIdTable() {
    val uuid = uuid("uuid").uniqueIndex()
    val spread = reference("spread", Spreads)
    val user = reference("user", Users)
    var quantity = integer("quantity")
}

class UserWishlistItem(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<UserWishlistItem>(UserWishlistItems)
    var uuid by UserWishlistItems.uuid
    var spread by Spread referencedOn UserWishlistItems.spread
    var user by User referencedOn UserWishlistItems.user
    var quantity by UserWishlistItems.quantity
}
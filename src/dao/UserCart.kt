package com.hajbo.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object UserCartItems: IntIdTable() {
    val uuid = uuid("uuid").uniqueIndex()
    val spread = reference("spread", Spreads)
    val user = reference("user", Users)
    var quantity = integer("quantity")
}

class UserCartItem(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<UserCartItem>(UserCartItems)
    var uuid by UserCartItems.uuid
    var spread by Spread referencedOn UserCartItems.spread
    var user by User referencedOn UserCartItems.user
    var quantity by UserCartItems.quantity
}
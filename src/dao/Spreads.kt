package com.hajbo.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Spreads: IntIdTable() {
    val uuid = uuid("uuid").uniqueIndex()
    val grandma = reference("grandma", Grandmas)
    val spreadType = varchar("spreadType", 10)
    val name = varchar("name", 250)
    val remaining = integer("remaining")
}

class Spread(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Spread>(Spreads)

    var uuid by Spreads.uuid
    var grandma by Grandma referencedOn Spreads.grandma
    var spreadType by Spreads.spreadType
    var name by Spreads.name
    var remaining by Spreads.remaining
}
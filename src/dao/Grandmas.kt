package com.hajbo.dao

import com.hajbo.dao.Spreads.uniqueIndex
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Grandmas: IntIdTable() {
    val uuid = uuid("uuid").uniqueIndex()
    val username = varchar("username", 50)
}

class Grandma(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Grandma>(Grandmas)

    var uuid by Grandmas.uuid
    var username by Grandmas.username

    val availableSpreads by Spread referrersOn Spreads.grandma
}
package com.hajbo.database

import com.hajbo.dao.Grandma
import com.hajbo.dao.Grandmas
import com.hajbo.database.DatabaseFactory.dbQuery
import com.hajbo.dto.GrandmaDto
import com.hajbo.dto.SpreadDto
import java.util.*

class GrandmaService {

    suspend fun getAllGrandmas(): List<GrandmaDto> = dbQuery {
        Grandma.all().map { toGrandmaDto(it)}
    }

    suspend fun getGrandma(id: UUID): Grandma = dbQuery {
        Grandma.find{ Grandmas.uuid eq id}.first()
    }

    suspend fun getAvailableSpreads(id: UUID): List<SpreadDto> = dbQuery {
        val grandma = getGrandma(id)
        grandma.availableSpreads.map { toSpreadDto(it) }
    }

    suspend fun addGrandma(username: String): Grandma? {
        val uuid = UUID.randomUUID()
        dbQuery {
            Grandma.new{
                this.uuid = uuid
                this.username = username
            }
        }
        return getGrandma(uuid)
    }
}
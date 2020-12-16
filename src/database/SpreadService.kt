package com.hajbo.database

import com.hajbo.dao.*
import com.hajbo.database.DatabaseFactory.dbQuery
import com.hajbo.dto.SpreadDto
import org.jetbrains.exposed.sql.select
import java.util.*

class SpreadService {

    suspend fun getAllSpreads(): List<SpreadDto> = dbQuery {
        Spread.all().map { toSpreadDto(it)}
    }

    suspend fun getSpread(uuid: UUID): Spread = dbQuery {
        Spread.find{ Spreads.uuid eq uuid}.first()
    }

    suspend fun getSpreadDto(uuid: UUID): SpreadDto = dbQuery {
        toSpreadDto(getSpread(uuid))
    }

    suspend fun addSpread(name: String, type: String, stock: Int, grandma: Grandma): Spread {
        val uuid = UUID.randomUUID()
        dbQuery {
            Spread.new {
                this.uuid = uuid
                this.name = name
                this.spreadType = type
                this.remaining = stock
                this.grandma = grandma
            }
        }
        return getSpread(uuid)
    }

    suspend fun restockSpread(uuid: UUID, stock: Int) {
        dbQuery {
            val spread = getSpread(uuid)
            spread.remaining = spread.remaining + stock

            val wishlistItems = UserWishlistItem.find{
                UserWishlistItems.spread.eq(spread.id)
            }
            for (item in wishlistItems) {
                sendEmail(
                        item.user,
                        "An item on your wishlist is available: ${spread.name}"
                )
            }
        }
    }

    suspend fun buySpread(uuid: UUID, quantity: Int) {
        dbQuery {
            var spread = getSpread(uuid)
            spread.remaining = spread.remaining - quantity
        }
    }
}
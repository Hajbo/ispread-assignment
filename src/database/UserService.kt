package com.hajbo.database

import com.hajbo.dao.*
import com.hajbo.database.DatabaseFactory.dbQuery
import com.hajbo.dto.UserDto
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import java.util.*

class UserService {

    suspend fun getAllUsers(): List<UserDto> = dbQuery {
        User.all().map { toUserDto(it) }
    }

    suspend fun getUser(id: UUID): User = dbQuery {
        User.find{Users.uuid eq id}.first()
    }

    suspend fun getUserDto(id: UUID): UserDto = dbQuery {
        val user = User.find{Users.uuid eq id}.first()
        toUserDto(user)
    }

    suspend fun getUserByName(username: String): User = dbQuery {
        User.find{Users.username eq username}.first()
    }

    suspend fun addUser(username: String, passwordHash: String, email: String, address: String): User? {
        val uuid = UUID.randomUUID()
        dbQuery {
            User.new {
                this.uuid = uuid
                this.username = username
                this.passwordHash = passwordHash
                this.email = email
                this.address = address
            }
        }
        return getUser(uuid)
    }

    suspend fun addSpreadToCart(user: User, spread: Spread, quantity: Int) {
        dbQuery {
            UserCartItem.new {
                this.uuid = UUID.randomUUID()
                this.spread = spread
                this.user = user
                this.quantity = quantity
            }
        }
    }

    suspend fun deleteSpreadFromCart(user: User, spread: Spread) {
        dbQuery {
            UserCartItems.deleteWhere {
                UserCartItems.user.eq(user.id) and UserCartItems.spread.eq(spread.id)
            }
        }
    }

    suspend fun addSpreadToWishlist(user: User, spread: Spread) {
        dbQuery {
            UserWishlistItem.new {
                this.uuid = UUID.randomUUID()
                this.spread = spread
                this.user = user
                this.quantity = 1
            }
        }
    }

    suspend fun deleteSpreadFromWishlist(user: User, spread: Spread) {
        dbQuery {
            UserWishlistItems.deleteWhere {
                UserWishlistItems.user.eq(user.id) and UserWishlistItems.spread.eq(spread.id)
            }
        }
    }

    suspend fun checkoutCart(user: User, spreadService: SpreadService) {
        dbQuery {
            for (cartItem in user.cart) {
                spreadService.buySpread(cartItem.spread.uuid, cartItem.quantity)
            }
            UserCartItems.deleteWhere {
                UserCartItems.user.eq(user.id)
            }
        }
    }

}
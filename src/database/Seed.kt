package com.hajbo.database

import com.hajbo.auth.hashFunction
import com.hajbo.dto.SpreadTypeEnum
import kotlinx.coroutines.runBlocking

fun seedDatabase() {
    val userService = UserService()
    val grandmaService = GrandmaService()
    val spreadService = SpreadService()

    runBlocking {
        val user1 = userService.addUser("Test1", hashFunction("test1"), "test1@bme.hu", "Budapest 1111")!!
        val user2 = userService.addUser("Test2", hashFunction("test2"), "test2@bme.hu", "Budapest 1112")!!
        val user3 = userService.addUser("Test3", hashFunction("test3"), "test3@bme.hu", "Budapest 1113")!!

        val grandma1 = grandmaService.addGrandma("G1")!!
        val grandma2 = grandmaService.addGrandma("G2")!!
        val grandma3 = grandmaService.addGrandma("G3")!!

        val spread1 = spreadService.addSpread("S1", SpreadTypeEnum.MEAT.type, 100, grandma1)
        val spread2 = spreadService.addSpread("S2", SpreadTypeEnum.DAIRY.type, 0, grandma1)
        val spread3 = spreadService.addSpread("S3", SpreadTypeEnum.VEGETABLE.type, 100, grandma2)
        val spread4 = spreadService.addSpread("S4", SpreadTypeEnum.DAIRY.type, 100, grandma2)
        val spread5 = spreadService.addSpread("S5", SpreadTypeEnum.MEAT.type, 0, grandma3)
        val spread6 = spreadService.addSpread("S6", SpreadTypeEnum.VEGETABLE.type, 100, grandma3)

        userService.addSpreadToCart(user1, spread6, 5)
        userService.addSpreadToCart(user1, spread1, 3)
        userService.addSpreadToCart(user2, spread4, 12)
        userService.addSpreadToCart(user2, spread6, 20)
        userService.addSpreadToCart(user3, spread3, 2)
        userService.addSpreadToCart(user3, spread1, 1)

        userService.addSpreadToWishlist(user1, spread2)
        userService.addSpreadToWishlist(user2, spread5)
    }
}

package com.hajbo.dto

import java.util.*

data class UserDto(val uuid: UUID, val username: String, val email: String, val address: String, val cart: List<UserSpreadDto>, val wishList: List<UserSpreadDto>)
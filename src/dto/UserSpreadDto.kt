package com.hajbo.dto

import java.util.*

data class UserSpreadDto (val uuid: UUID, val name: String, val type: String, val quantity: Int, val remaining: Int, val grandma: UUID )
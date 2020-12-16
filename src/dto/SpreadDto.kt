package com.hajbo.dto

import java.util.*

data class SpreadDto(val uuid: UUID, val name: String, val type: String, val remaining: Int, val grandma: UUID)
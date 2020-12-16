package com.hajbo.dto

import java.util.*

data class GrandmaDto (val uuid: UUID, val username: String, val availableSpreads: List<SpreadDto>)
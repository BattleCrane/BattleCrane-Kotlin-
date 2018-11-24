package com.orego.battlecrane.bcApi.manager.unit

object BUnitIdGenerator {

    private var idCounter = 0

    fun generateUnitId(): Int {
        return idCounter++
    }
}
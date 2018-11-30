package com.orego.battlecrane.bcApi.unit

object BUnitIdGenerator {

    private var unitIdCounter = 0

    private var actionIdCounter = 0

    fun generateUnitId() = this.unitIdCounter++

    fun generateActionId() = this.actionIdCounter++
}
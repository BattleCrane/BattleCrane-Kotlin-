package com.orego.battlecrane.bcApi.unit

object BIdGenerator {

    private var unitIdCounter = 0

    private var actionIdCounter : Long = 0

    fun generateUnitId() = this.unitIdCounter++

    fun generateActionId() = this.actionIdCounter++
}
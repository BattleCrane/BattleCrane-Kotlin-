package com.orego.battlecrane.bc.api.util

object BIdGenerator {

    private var unitIdCounter : Long = 0

    private var actionIdCounter : Long = 0

    private var playerIdConter : Long = 1

    fun generateUnitId() = this.unitIdCounter++

    fun generateActionId() = this.actionIdCounter++

    fun generatePlayerId() = this.playerIdConter++
}
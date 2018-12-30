package com.orego.battlecrane.bc.api.util

object BIdGenerator {

    private var unitIdCounter : Long = 0

    private var actionIdCounter : Long = 0

    private var playerIdConter : Long = 1

    private var nodeIdCounter : Long = 0

    private var pipeIdConter : Long = 0

    private var attackableIdCounter: Long = 0

    fun generateUnitId() = this.unitIdCounter++

    fun generateActionId() = this.actionIdCounter++

    fun generatePlayerId() = this.playerIdConter++

    fun generatePipeId() = this.pipeIdConter++

    fun generateNodeId() = this.nodeIdCounter++

    fun generateAttackableId() = this.attackableIdCounter++
}
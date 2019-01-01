package com.orego.battlecrane.bc.api.context.generator

class BIdGenerator {

    /**
     * Unit.
     */

    private var unitIdCounter : Long = 0

    fun generateUnitId() = this.unitIdCounter++

    /**
     * Player.
     */

    private var playerIdConter : Long = 1

    fun generatePlayerId() = this.playerIdConter++

    /**
     * Context.
     */

    private var nodeIdCounter : Long = 0

    private var pipeIdCounter : Long = 0

    fun generatePipeId() = this.pipeIdCounter++

    fun generateNodeId() = this.nodeIdCounter++

    /**
     * Property.
     */

    private var attackableIdCounter: Long = 0

    private var hitPointableIdCounter : Long = 0

    private var levelableIdCounter : Long = 0

    private var producableIdCounter : Long = 0

    fun generateAttackableId() = this.attackableIdCounter++

    fun generateHitPointableId() = this.hitPointableIdCounter++

    fun generateLevelableId() = this.levelableIdCounter++

    fun generateProducableId() = this.producableIdCounter++
}
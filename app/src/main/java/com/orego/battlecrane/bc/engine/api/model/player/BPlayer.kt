package com.orego.battlecrane.bc.engine.api.model.player

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BUnitHeap

open class BPlayer protected constructor(context: BGameContext) {

    companion object {

        const val NEUTRAL_ID: Long = 0
    }

    /**
     * Id.
     */

    val playerId: Long = context.contextGenerator.getIdGenerator(BPlayer::class.java).generateId()

    /**
     * Property.
     */

    val allies = mutableSetOf<Long>()

    val enemies = mutableSetOf<Long>()

    open fun getUnits(context: BGameContext) = context.storage
        .getHeap(BUnitHeap::class.java).objectMap.values
        .filter { unit -> unit.playerId == this.playerId }

    /**
     * Player.
     */

    fun addEnemy(playerId: Long) = this.enemies.add(playerId)

    fun removeEnemy(playerId: Long) = this.enemies.remove(playerId)

    fun isMine(playerId: Long) = this.playerId == playerId

    fun isEnemy(playerId: Long) = this.enemies.contains(playerId)

    fun addAlly(playerId: Long) = this.allies.add(playerId)

    fun removeAlly(playerId: Long) = this.allies.remove(playerId)

    fun isAlly(playerId: Long) = this.allies.contains(playerId)

    /**
     * Configure player.
     */

    open class Builder {

        open fun build(context: BGameContext) = BPlayer(context)
    }
}
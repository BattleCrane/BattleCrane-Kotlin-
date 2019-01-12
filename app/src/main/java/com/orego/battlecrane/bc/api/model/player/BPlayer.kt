package com.orego.battlecrane.bc.api.model.player

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BAdjutantHeap

open class BPlayer private constructor(context: BGameContext) {

    companion object {

        const val NEUTRAL_PLAYER_ID: Long = 0
    }

    /**
     * Id.
     */

    val playerId: Long = context.contextGenerator.getIdGenerator(BPlayer::class.java).generateId()

    /**
     * Property.
     */

    fun isAblePlayer(context: BGameContext): Boolean {
        val adjutantHeap = context.storage.getHeap(BAdjutantHeap::class.java)
        for (id in this.adjutants) {
            val adjutant = adjutantHeap[id]
            if (adjutant.isAble) {
                return true
            }
        }
        return false
    }

    val adjutants = mutableSetOf<Long>()

    val allies = mutableSetOf<Long>()

    val enemies = mutableSetOf<Long>()

    /**
     * Player.
     */

    fun addEnemy(player: Long) = this.enemies.add(player)

    fun removeEnemy(player: Long) = this.enemies.remove(player)

    fun isMine(player: Long) = this.playerId == player

    fun isEnemy(player: Long) = this.enemies.contains(player)

    fun addAlly(player: Long) = this.allies.add(player)

    fun removeAlly(player: Long) = this.allies.remove(player)

    fun isAlly(player: Long) = this.allies.contains(player)

    /**
     * Configure player.
     */

    open class Builder {

        open fun build(context: BGameContext) = BPlayer(context)
    }
}
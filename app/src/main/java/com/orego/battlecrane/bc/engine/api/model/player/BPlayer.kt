package com.orego.battlecrane.bc.engine.api.model.player

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BAdjutantHeap
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

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

    val adjutants = mutableSetOf<Long>()

    val allies = mutableSetOf<Long>()

    val enemies = mutableSetOf<Long>()

    open fun isAblePlayer(context: BGameContext): Boolean {
        val adjutantHeap = context.storage.getHeap(BAdjutantHeap::class.java)
        for (id in this.adjutants) {
            val adjutant = adjutantHeap[id]
            if (adjutant.isAble) {
                return true
            }
        }
        return false
    }

    open fun getUnits(context: BGameContext): List<BUnit> {
        return context.storage.getHeap(BUnitHeap::class.java).objectMap.values.filter { unit ->
            unit.playerId == this.playerId
        }
    }

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
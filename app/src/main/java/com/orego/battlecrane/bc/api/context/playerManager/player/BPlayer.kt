package com.orego.battlecrane.bc.api.context.playerManager.player

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.playerManager.player.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.api.util.BIdGenerator

class BPlayer(context: BGameContext, builder: BAdjutant.Builder) {

    val id: Long = BIdGenerator.generatePlayerId()

    //TODO WHILE WITHOUT BONUSES:
    val adjutant: BAdjutant = builder.build(context, this.id)

    private val allies = mutableSetOf<Long>()

    private val enemies = mutableSetOf<Long>()

    /**
     * Game.
     */

    fun startTurn() {
        this.adjutant.onTurnStarted()
    }

    fun finishTurn() {
        this.adjutant.onTurnEnded()
    }

    /**
     * Player.
     */

    fun addEnemy(player: Long) = this.enemies.add(player)

    fun removeEnemy(player: Long) = this.enemies.remove(player)

    fun isEnemy(player: Long) = this.enemies.contains(player)

    fun addAlly(player: Long) = this.allies.add(player)

    fun removeAlly(player: Long) = this.allies.remove(player)

    fun isAlly(player: Long) = this.allies.contains(player)
}
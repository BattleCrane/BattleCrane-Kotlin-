package com.orego.battlecrane.bc.api.context.playerManager.player

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.playerManager.player.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.context.playerManager.player.turnTimer.BTurnTimer
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.api.util.BIdGenerator

class BPlayer(context: BGameContext, builder : BAdjutant.Builder) {

    val id: Long = BIdGenerator.generatePlayerId()

    //TODO WHILE WITHOUT BONUSES:
    val adjutant: BAdjutant = builder.build(context, this)

    val turnTimer = BTurnTimer(this.adjutant)

    private val allies = mutableSetOf<BPlayer>()

    private val enemies = mutableSetOf<BPlayer>()

    fun owns(unit : BUnit) = this == unit.owner

    /**
     * Player.
     */

    fun addEnemy(player: BPlayer) = this.enemies.add(player)

    fun removeEnemy(player: BPlayer) = this.enemies.remove(player)

    fun isEnemy(player: BPlayer?) = this.enemies.contains(player)

    fun addAlly(player: BPlayer) = this.allies.add(player)

    fun removeAlly(player: BPlayer) = this.allies.remove(player)

    fun isAlly(player: BPlayer) = this.allies.contains(player)
}
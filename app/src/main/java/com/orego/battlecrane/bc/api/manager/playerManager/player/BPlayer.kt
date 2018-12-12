package com.orego.battlecrane.bc.api.manager.playerManager.player

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.mechanics.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.manager.mechanics.turnTimer.BTurnTimer
import com.orego.battlecrane.bc.api.model.unit.BUnit

class BPlayer(context: BGameContext, private val id: Long, builder : BAdjutant.Builder) {

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

    fun isEnemy(player: BPlayer) = this.enemies.contains(player)

    fun addAlly(player: BPlayer) = this.allies.add(player)

    fun removeAlly(player: BPlayer) = this.allies.remove(player)

    fun isAlly(player: BPlayer) = this.allies.contains(player)
}
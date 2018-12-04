package com.orego.battlecrane.bcApi.manager.playerManager.team.player

import com.orego.battlecrane.bcApi.model.tools.BTools

class BPlayer {

    lateinit var tools: BTools

    private val allies = mutableSetOf<BPlayer>()

    private val enemies = mutableSetOf<BPlayer>()

    fun addEnemy(player: BPlayer) = this.enemies.add(player)

    fun removeEnemy(player: BPlayer) = this.enemies.remove(player)

    fun isEnemy(player: BPlayer) = this.enemies.contains(player)

    fun addAlly(player: BPlayer) = this.allies.add(player)

    fun removeAlly(player: BPlayer) = this.allies.remove(player)

    fun isAlly(player: BPlayer) = this.allies.contains(player)
}
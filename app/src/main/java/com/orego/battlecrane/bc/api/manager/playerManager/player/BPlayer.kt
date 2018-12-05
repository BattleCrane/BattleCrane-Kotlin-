package com.orego.battlecrane.bc.api.manager.playerManager.player

import com.orego.battlecrane.bc.api.model.action.BAction

class BPlayer {

    lateinit var tools: BTools

    private val allies = mutableSetOf<BPlayer>()

    private val enemies = mutableSetOf<BPlayer>()

    /**
     * Interaction.
     */

    fun addEnemy(player: BPlayer) = this.enemies.add(player)

    fun removeEnemy(player: BPlayer) = this.enemies.remove(player)

    fun isEnemy(player: BPlayer) = this.enemies.contains(player)

    fun addAlly(player: BPlayer) = this.allies.add(player)

    fun removeAlly(player: BPlayer) = this.allies.remove(player)

    fun isAlly(player: BPlayer) = this.allies.contains(player)

    /**
     * Tools.
     */

    open class BTools(
        val buildingStack: MutableList<Class<out BAction>>,
        val armyStack: MutableList<Class<out BAction>>,
        val bonusStack: MutableList<Class<out BAction>>
    ) {

        companion object {

            const val DEFAULT_RESOURCE_COUNT = 0
        }

        var resourceCount : Int = 0
    }
}
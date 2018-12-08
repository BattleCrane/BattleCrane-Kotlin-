package com.orego.battlecrane.bc.api.manager.playerManager.player

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.model.action.BAction

class BPlayer(id: Long) {

    lateinit var tools: Tools

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

    abstract class Tools(

        val buildingStack: MutableSet<Pair<BAction, Int>>,

        val armyStack: MutableSet<Pair<BAction, Int>>,

        val bonusStack: MutableSet<Pair<BAction, Int>>
    ) {

        companion object {

            const val DEFAULT_RESOURCE_COUNT = 0
        }

        var resourceCount: Int = DEFAULT_RESOURCE_COUNT
    }

    object Builder {

        fun id(id: Long) = BPlayer(id)
    }
}
package com.orego.battlecrane.bc.std.race.human.action.build

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.model.contract.BTargetable
import com.orego.battlecrane.bc.std.race.human.action.BHumanAction
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanBarracks
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanFactory

class BHumanBuildFactory(gameContext: BGameContext, owner: BPlayer) : BHumanAction(gameContext, owner), BTargetable {

    override var targetPosition: BPoint? = null

    override fun performAction(): Boolean {
        if (this.targetPosition != null && this.owner != null) {
            val factory = BHumanFactory(this.gameContext, this.owner!!)
            val manager = this.gameContext.mapManager
            return manager.createUnit(factory, this.targetPosition)
        }
        return false
    }

    class Producer(context: BGameContext, owner: BPlayer) : BAction.Producer(context, owner) {

        private val unitHeap by lazy { context.mapManager.unitHeap.values }

        override fun produceToStackByAbility(stack: MutableSet<BAction>, abilityCount: Int) {
            if (abilityCount > 0) {
                var barracksCount = 0
                var factoryCount = 0
                this.unitHeap.forEach { unit ->
                    if (this.owner.owns(unit)) {
                        when (unit) {
                            is BHumanBarracks -> barracksCount++
                            is BHumanFactory -> factoryCount++
                        }
                    }
                }
                val comparison = barracksCount - factoryCount
                if (comparison > 0) {
                    stack.add(BHumanBuildFactory(this.context, this.owner))
                }
            }
        }
    }
}
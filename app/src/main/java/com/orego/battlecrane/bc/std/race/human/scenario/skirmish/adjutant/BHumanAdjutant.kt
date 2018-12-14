package com.orego.battlecrane.bc.std.race.human.scenario.skirmish.adjutant

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.mechanics.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.model.contract.BAttackable
import com.orego.battlecrane.bc.api.model.contract.BProducable
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanBarracks
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanFactory
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanHeadquarters

class BHumanAdjutant(
    context: BGameContext,
    owner: BPlayer,
    bonusFactories: MutableSet<BAction.Factory>
) : BAdjutant(context, owner, bonusFactories) {


    private val unitHeap by lazy {
        this.context.mapManager.unitHeap.values
    }

    private val alertManager = AlertManager()

    private val resourceManager = ResourceManager()

    override fun onGameStarted() {
    }

    override fun onTurnStarted() {
        this.alertManager.activateUnits()
        this.resourceManager.loadResources()
    }

    override fun onTurnEnded() {
    }

    /**
     * AlertManager.
     */

    private inner class AlertManager {


        fun activateUnits() {
            this.activateProducers()
            this.activateAttackers()
        }

        private fun activateAttackers() {
            this.unitHeap
                .filter { unit -> this@BHumanAdjutant.owner.owns(unit) && unit is BAttackable }
                .map { unit -> unit as BAttackable }
                .forEach { unit -> unit.switchAttackEnable(true) }
        }

        private fun activateProducers() {

        }
    }

    /**
     * Resource manager.
     */

    private inner class ResourceManager : BAdjutant.ResourceManager() {

        fun loadResources() {
            val context = this@BHumanAdjutant.context
            val owner = this@BHumanAdjutant.owner
            this.buildingActions.clear()
            this.armyActions.clear()
            //Update:
            this.influenceCount++
            this@BHumanAdjutant.unitHeap
                .filter { unit -> unit is BProducable && owner.owns(unit) }
                .forEach { unit ->
                    unit as BProducable
                    unit.switchProduceEnable(true)
                    this.collect(unit.getProduceActions(context, owner))
                }
        }

        fun refreshResources() {
            val context = this@BHumanAdjutant.context
            val owner = this@BHumanAdjutant.owner
            this.buildingActions.clear()
            this.armyActions.clear()
            this@BHumanAdjutant.unitHeap
                .filter { unit -> unit is BProducable && owner.owns(unit) }
                .forEach { unit ->
                    unit as BProducable
                    this.collect(unit.getProduceActions(context, owner))
                }
        }

        private fun collect(actions : Set<BAction>) {
            actions.forEach { action ->
                when (action){
                    is BHumanHeadquarters.Build -> this.buildingActions += action
                    is BHumanBarracks.TrainMarine -> this.armyActions += action
                    is BHumanFactory.TrainTank -> this.armyActions += action
                }
            }
        }
    }
}
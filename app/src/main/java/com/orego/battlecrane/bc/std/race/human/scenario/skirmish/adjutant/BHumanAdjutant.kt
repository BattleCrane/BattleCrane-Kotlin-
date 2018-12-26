package com.orego.battlecrane.bc.std.race.human.scenario.skirmish.adjutant

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.BActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate.node.BOnCreateActionNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onPerform.node.BOnPerformActionNode
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.context.playerManager.player.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.model.contract.BAttackable
import com.orego.battlecrane.bc.api.model.contract.BProducable
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanBarracks
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanFactory
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanHeadquarters

class BHumanAdjutant(
    context: BGameContext, owner: Long, bonusFactories: MutableSet<BAction.Factory>
) : BAdjutant(context, owner, bonusFactories) {

    companion object {

        private const val MAX_RECEIVED_INFLUENCE_COUNT = 20

        private const val TURN_INFLUENCE_COUNT = 1
    }

    override val resourceManager = ResourceManager()

    fun getUnitHeap() = this.context.mapManager.unitHeap.values

    override fun onGameStarted() {
    }

    override fun onTurnStarted() {
        this.resourceManager.loadResources()
        this.attackManager.startTurn()
    }

    override fun onTurnEnded() {
    }

    /**
     * AttackManager.
     */

    fun startTurn() {
        this.activateUnits()
        val isHeadquartersShotIsReady =
            this@BHumanAdjutant.resourceManager.receivedInfluenceCount == MAX_RECEIVED_INFLUENCE_COUNT
        if (isHeadquartersShotIsReady) {
            this.makeHeadquatersShot()
        }
    }

    fun activateUnits() {
        this@BHumanAdjutant.unitHeap
            .filter { unit -> this@BHumanAdjutant.ownerId.owns(unit) && unit is BAttackable }
            .map { unit -> unit as BAttackable }
            .forEach { unit -> unit.switchAttackEnable(true) }
    }

    private fun makeHeadquatersShot() {

    }

    /**
     * Resource manager.
     */

    inner class ResourceManager : BAdjutant.ResourceManager() {

        init {
            val pipeline = this@BHumanAdjutant.context.pipeline
            val actionPipe = pipeline.findPipe(BActionPipe.NAME)!!
            actionPipe
                .findPipe(BOnCreateActionNode.DEFAULT_PIPE_NAME)!!
                .placeNode(OnActionCreatedNode())
            actionPipe
                .findPipe(BOnPerformActionNode.DEFAULT_PIPE_NAME)!!
                .placeNode(OnActionPerformedNode())
        }

        fun loadResources() {
            val context = this@BHumanAdjutant.context
            val owner = this@BHumanAdjutant.ownerId
            this.buildingActions.clear()
            this.trainActions.clear()
            //Update:
            if (this.receivedInfluenceCount < MAX_RECEIVED_INFLUENCE_COUNT) {
                this.currentInfluenceCount += TURN_INFLUENCE_COUNT
                this.receivedInfluenceCount += TURN_INFLUENCE_COUNT
            }
            this@BHumanAdjutant.unitHeap
                .filter { unit -> unit is BProducable && owner.owns(unit) }
                .forEach { unit ->
                    unit as BProducable
                    unit.switchProduceEnable(true)
                    unit.pushProduceActions(context, owner)
                }
        }

        /**
         * Collects actions after creating.
         */

        inner class OnActionCreatedNode : BEventPipeline.Pipe.Node(this@BHumanAdjutant.context) {

            override val name = "ACTION_LOADER_NODE"

            override fun handle(event: BEvent) {
                val bundle = event.bundle!! as BOnCreateActionNode.Bundle
                val action = bundle.action
                if (this@BHumanAdjutant.ownerId == action.owner) {
                    when (action) {
                        is BHumanHeadquarters.Build -> this@ResourceManager.buildingActions += action
                        is BHumanBarracks.TrainMarine -> this@ResourceManager.trainActions += action
                        is BHumanFactory.TrainTank -> this@ResourceManager.trainActions += action
                    }
                }
            }
        }

        /**
         * Refresh action list after action running.
         */

        inner class OnActionPerformedNode : BEventPipeline.Pipe.Node(this@BHumanAdjutant.context) {

            override val name = "ACTION_PERFORMED_NODE"

            override fun handle(event: BEvent) {
                val bundle = event.bundle!! as BOnCreateActionNode.Bundle
                val action = bundle.action
                if (this@BHumanAdjutant.ownerId == action.owner) {
                    this.refreshResources()
                }
            }

            fun refreshResources() {
                val context = this@BHumanAdjutant.context
                val owner = this@BHumanAdjutant.ownerId
                this@ResourceManager.buildingActions.clear()
                this@ResourceManager.trainActions.clear()
                this@BHumanAdjutant.unitHeap
                    .filter { unit -> unit is BProducable && owner.owns(unit) }
                    .forEach { unit ->
                        unit as BProducable
                        unit.pushProduceActions(context, owner)
                    }
            }
        }
    }

    /**
     * Builder.
     */

    class Builder : BAdjutant.Builder() {

        override fun build(context: BGameContext, owner: Long) = BHumanAdjutant(context, owner, this.bonusFactories)
    }
}
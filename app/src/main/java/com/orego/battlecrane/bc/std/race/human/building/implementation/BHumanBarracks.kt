package com.orego.battlecrane.bc.std.race.human.building.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.pipe.onTurnFinished.node.BOnTurnFinishedNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.pipe.onTurnStarted.node.BOnTurnStartedNode
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.model.contract.BHitPointable
import com.orego.battlecrane.bc.api.model.contract.BLevelable
import com.orego.battlecrane.bc.api.model.contract.BProducable
import com.orego.battlecrane.bc.api.model.contract.BTargetable
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.action.BHumanAction
import com.orego.battlecrane.bc.std.race.human.building.BHumanBuilding
import com.orego.battlecrane.bc.std.race.human.infantry.implementation.BHumanMarine


class BHumanBarracks(context: BGameContext, owner: BPlayer) : BHumanBuilding(context, owner), BHitPointable,
    BLevelable, BProducable {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 2

        private const val DEFAULT_HORIZONTAL_SIDE = 1

        private const val DEFAULT_MAX_HEALTH = 1

        private const val DEFAULT_LEVEL = 1

        private const val DEFAULT_MAX_LEVEL = 3
    }

    /**
     * Properties.
     */

    override val verticalSize = DEFAULT_VERTICAL_SIDE

    override val horizontalSize = DEFAULT_HORIZONTAL_SIDE

    override var currentHitPoints = DEFAULT_MAX_HEALTH

    override var maxHitPoints = DEFAULT_MAX_HEALTH

    override var currentLevel = DEFAULT_LEVEL

    override var maxLevel = DEFAULT_MAX_LEVEL

    override var isProduceEnable = false

    /**
     * Factory.
     */

    val trainMarineLvl1Factory = TrainMarineLvl1Factory()

    val trainMarineLvl2Factory = TrainMarineLvl2Factory()

    val trainMarineLvl3Factory = TrainMarineLvl3Factory()


    /**
     * Context.
     */

    private val pipeline = this.context.pipeline

    private val onTurnStartedPipe = OnTurnStartedPipe(context)

    private val onTurnFinishedPipe = OnTurnFinishedPipe(context)

    init {
        this.pipeline
            .findNode(BOnTurnStartedNode.NAME)!!
            .connectPipe(this.onTurnStartedPipe)
        this.pipeline
            .findNode(BOnTurnFinishedNode.NAME)!!
            .connectPipe(this.onTurnFinishedPipe)
    }

    override fun produceUnits() {
        if (this.isProduceEnable) {
            this.trainMarineLvl1Factory.sendOnCreateUnitAction()
            if (this.currentLevel > 1) {
                this.trainMarineLvl2Factory.sendOnCreateUnitAction()
                if (this.currentLevel > 2) {
                    this.trainMarineLvl3Factory.sendOnCreateUnitAction()
                }
            }
        }
    }

    /**
     * Action.
     */

    abstract inner class TrainMarine :
        BHumanAction(this.context, this.owner!!), BTargetable {

        override var targetPosition: BPoint? = null

        protected abstract fun isTrainConditionPerformed(unit: BUnit): Boolean

        override fun perform(): Boolean {
            if (this.targetPosition != null) {
                val marine = BHumanMarine(this.context, this.owner!!)
                val manager = this.context.mapManager
                val unit = manager.getUnitByPosition(this.targetPosition)
                if (this.isTrainConditionPerformed(unit)) {

                    //TODO: MAKE!!!
                    val isSuccessful = manager.createUnit(marine, this.targetPosition)
                    if (isSuccessful) {
                        this@BHumanBarracks.switchProduceEnable(false)
                    }
                    return isSuccessful
                }
            }
            return false
        }
    }

    /**
     * Factories.
     */

    inner class TrainMarineLvl1Factory : BAction.Factory(this.pipeline) {

        override fun createAction() = Action()

        inner class Action : TrainMarine() {

            override fun isTrainConditionPerformed(unit: BUnit) =
                this@BHumanBarracks.owner!!.owns(unit)
        }
    }

    inner class TrainMarineLvl2Factory : BAction.Factory(this.pipeline) {

        override fun createAction() = Action()

        inner class Action : TrainMarine() {

            override fun isTrainConditionPerformed(unit: BUnit) =
                !this@BHumanBarracks.owner!!.isEnemy(unit.owner)
        }
    }

    inner class TrainMarineLvl3Factory : BAction.Factory(this.pipeline) {

        override fun createAction() = Action()

        inner class Action : TrainMarine() {

            override fun isTrainConditionPerformed(unit: BUnit) = true
        }
    }

    /**
     * Pipe.
     */

    inner class OnTurnStartedPipe(context: BGameContext) : BPipe(context) {

        override val name = "BARRACKS_ON_TURN_STARTED_${this@BHumanBarracks.owner!!.id}"

        override val nodes = mutableListOf<BNode>(OnTurnStartedNode(context))
    }

    inner class OnTurnFinishedPipe(context: BGameContext) : BPipe(context) {

        override val name = "BARRACKS_ON_TURN_FINISHED_${this@BHumanBarracks.owner!!.id}"

        override val nodes = mutableListOf<BNode>(OnTurnFinishedNode(context))
    }

    /**
     * Node.
     */

    inner class OnTurnStartedNode(context: BGameContext) : BNode(context) {

        override val name = "BARRACKS_ON_TURN_STARTED_${this@BHumanBarracks.owner!!.id}"

        override fun handle(event: BEvent): BEvent? {
            val bundle = event.bundle
            return if (bundle is BOnTurnStartedPipe.OnTurnStartedBundle) {
                if (this@BHumanBarracks.owner == bundle.player) {

                    this.pipeMap.values.forEach { it.push(event) }
                }
                event
            } else {
                null
            }
        }
    }

    inner class OnTurnFinishedNode(context: BGameContext) : BNode(context) {

        override val name = "BARRACKS_ON_TURN_FINISHED_${this@BHumanBarracks.owner!!.id}"

        override fun handle(event: BEvent): BEvent? {
            //return if (event.bundle is BProducePipe.ProduceBundle) {
            //  this.pipeMap.values.forEach { it.push(event) }
            //event
            //} else {
            //  null
            //}
        }
    }
}
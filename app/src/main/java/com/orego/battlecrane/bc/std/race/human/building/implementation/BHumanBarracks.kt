package com.orego.battlecrane.bc.std.race.human.building.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate.BOnCreateActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate.node.BOnCreateActionNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.node.pipe.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.pipe.onTurnFinished.node.BOnTurnFinishedNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.pipe.onTurnStarted.node.BOnTurnStartedNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.node.pipe.BOnCreateUnitPipe
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.contract.BHitPointable
import com.orego.battlecrane.bc.api.model.contract.BLevelable
import com.orego.battlecrane.bc.api.model.contract.BProducable
import com.orego.battlecrane.bc.api.model.contract.BTargetable
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.action.BHumanAction
import com.orego.battlecrane.bc.std.race.human.building.BHumanBuilding
import com.orego.battlecrane.bc.std.race.human.infantry.implementation.BHumanMarine


class BHumanBarracks(context: BGameContext, ownerId: Long) :
    BHumanBuilding(ownerId), BHitPointable, BLevelable, BProducable {

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
     * Context.
     */

    private val onCreateActionPipeId: Long

    private val onTurnStartedPipeId: Long

    private val onTurnFinishedPipeId: Long

    init {
        val pipeline = context.pipeline
        val onCreateActionPipe = BPipe(context, mutableListOf(TrainMarineActionNode(context, ownerId)))
        val onTurnStartedPipe = BPipe(context)
        val onTurnFinishedPipe = BPipe(context)
        this.onCreateActionPipeId = onCreateActionPipe.id
        this.onTurnStartedPipeId = onTurnStartedPipe.id
        this.onTurnFinishedPipeId = onTurnFinishedPipe.id
        pipeline
            .findNode(BOnCreateActionNode.NAME)
            .connectPipe(onCreateActionPipe)
        pipeline
            .findNode(BOnTurnStartedNode.NAME)
            .connectPipe(this.onTurnStartedPipe)
        pipeline
            .findNode(BOnTurnFinishedNode.NAME)
            .connectPipe(this.onTurnFinishedPipe)
    }

    override fun produceUnits(pipeline: BEventPipeline) {
        if (this.isProduceEnable) {
            pipeline.pushEvent(
                TrainMarineActionNode.createTrainMarineLvl1ActionEvent(this.ownerId)
            )
            if (this.currentLevel > 1) {
                pipeline.pushEvent(
                    TrainMarineActionNode.createTrainMarineLvl2ActionEvent(this.ownerId)
                )
                if (this.currentLevel > 2) {
                    pipeline.pushEvent(
                        TrainMarineActionNode.createTrainMarineLvl3ActionEvent(this.ownerId)
                    )
                }
            }
        }
    }

    class TrainMarineActionNode(context: BGameContext, private val ownerId: Long) : BNode(context) {

        companion object {

            fun createTrainMarineLvl1ActionEvent(ownerId: Long) = TrainMarineLvl1ActionEvent(ownerId)

            fun createTrainMarineLvl2ActionEvent(ownerId: Long) = TrainMarineLvl1ActionEvent(ownerId)

            fun createTrainMarineLvl3ActionEvent(ownerId: Long) = TrainMarineLvl1ActionEvent(ownerId)
        }

        override fun handle(event: BEvent): BEvent? {
            val currentPlayer = this.context.playerManager.currentPlayer
            if (this.ownerId == currentPlayer.id) {
                val action = when (event) {
                    is TrainMarineLvl1ActionEvent -> TrainMarineLvl1(this.ownerId)
                    is TrainMarineLvl2ActionEvent -> TrainMarineLvl2(this.ownerId)
                    is TrainMarineLvl3ActionEvent -> TrainMarineLvl3(this.ownerId)
                    else -> return null
                }

            }
        }

        /**
         * Events.
         */

        abstract class TrainMarineActionEvent(val ownerId: Long) : BOnCreateActionPipe.OnCreateActionEvent()

        class TrainMarineLvl1ActionEvent(ownerId: Long) : TrainMarineActionEvent(ownerId)

        class TrainMarineLvl2ActionEvent(ownerId: Long) : TrainMarineActionEvent(ownerId)

        class TrainMarineLvl3ActionEvent(ownerId: Long) : TrainMarineActionEvent(ownerId)

        /**
         * Action.
         */

        abstract class TrainMarine(ownerId: Long) : BHumanAction(ownerId), BTargetable {

            override var targetPosition: BPoint? = null

            protected abstract fun isTrainConditionPerformed(unit: BUnit, player: BPlayer): Boolean

            override fun perform(context: BGameContext): Boolean {
                val position = this.targetPosition
                if (position != null) {
                    val unit = context.mapManager.getUnitByPosition(position)
                    val player = context.playerManager.getPlayerById(this.ownerId)
                    if (this.isTrainConditionPerformed(unit, player)) {
                        val pipeline = context.pipeline
                        pipeline.pushEvent(
                            BOnCreateUnitPipe.createEvent(marine, position)
                        )
                        pipeline.pushEvent(
                            BOnProduceEnablePipe.createEvent(this@BHumanBarracks, false)
                        )
                        return true
                    }
                }
                return false
            }
        }

        class TrainMarineLvl1(ownerId: Long) : TrainMarine(ownerId) {

            override fun isTrainConditionPerformed(unit: BUnit, player: BPlayer) =
                this.ownerId == unit.ownerId
        }

        class TrainMarineLvl2(ownerId: Long) : TrainMarine(ownerId) {

            override fun isTrainConditionPerformed(unit: BUnit, player: BPlayer) =
                !player.isEnemy(unit.ownerId)
        }

        class TrainMarineLvl3(ownerId: Long) : TrainMarine(ownerId) {

            override fun isTrainConditionPerformed(unit: BUnit, player: BPlayer) = true
        }
    }

    /**
     * Pipe.
     */

    inner class OnTurnStartedPipe(context: BGameContext) : BPipe(context) {

        override val name = "BARRACKS_ON_TURN_STARTED_${this@BHumanBarracks.ownerId!!.id}"

        override val nodes = mutableListOf<BNode>(OnTurnStartedNode(context))
    }

    inner class OnTurnFinishedPipe(context: BGameContext) : BPipe(context) {

        override val name = "BARRACKS_ON_TURN_FINISHED_${this@BHumanBarracks.ownerId!!.id}"

        override val nodes = mutableListOf<BNode>(OnTurnFinishedNode(context))
    }

    /**
     * Node.
     */

    inner class OnTurnStartedNode(context: BGameContext) : BNode(context) {

        override val name = "BARRACKS_ON_TURN_STARTED_${this@BHumanBarracks.ownerId!!.id}"

        override fun handle(event: BEvent): BEvent? {
            val bundle = event.bundle
            return if (bundle is BOnTurnStartedPipe.OnTurnStartedBundle) {
                if (this@BHumanBarracks.ownerId == bundle.player) {

                    this.pipeMap.values.forEach { it.push(event) }
                }
                event
            } else {
                null
            }
        }
    }

    inner class OnTurnFinishedNode(context: BGameContext) : BNode(context) {

        override val name = "BARRACKS_ON_TURN_FINISHED_${this@BHumanBarracks.ownerId!!.id}"

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
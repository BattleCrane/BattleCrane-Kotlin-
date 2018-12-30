package com.orego.battlecrane.bc.std.race.human.unit.building.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.model.annotation.unitComponent.BUnitComponent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate.BOnCreateActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate.node.BOnCreateActionNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.node.pipe.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.contract.BHitPointable
import com.orego.battlecrane.bc.api.model.contract.BLevelable
import com.orego.battlecrane.bc.api.model.contract.BProducable
import com.orego.battlecrane.bc.api.model.contract.BTargetable
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.action.BHumanAction
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding
import com.orego.battlecrane.bc.std.race.human.unit.infantry.implementation.BHumanMarine


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

    private val onCreateTrainMarineActionPipeId: Long

    init {
        //Create pipes:
        val onCreateMarineTrainActionPipe = BPipe(
            context, mutableListOf(OnCreateTrainMarineActionNode(context, ownerId, this.unitId))
        )
        //Save pipe ids:
        this.onCreateTrainMarineActionPipeId = onCreateMarineTrainActionPipe.id
        //Bind pipes:
        context.pipeline.bindPipeToNode(BOnCreateActionNode.NAME, onCreateMarineTrainActionPipe)
    }

    /**
     * Sends events to pipeline to create produce abilities.
     */

    override fun produceAbilities(pipeline: BEventPipeline) {
        if (this.isProduceEnable) {
            pipeline.pushEvent(
                OnCreateTrainMarineActionNode.createTrainMarineLvl1ActionEvent(this.ownerId, this.unitId)
            )
            if (this.currentLevel > 1) {
                pipeline.pushEvent(
                    OnCreateTrainMarineActionNode.createTrainMarineLvl2ActionEvent(this.ownerId, this.unitId)
                )
                if (this.currentLevel > 2) {
                    pipeline.pushEvent(
                        OnCreateTrainMarineActionNode.createTrainMarineLvl3ActionEvent(this.ownerId, this.unitId)
                    )
                }
            }
        }
    }

    /**
     * Action.
     */

    abstract class TrainMarine(ownerId: Long, private val barracksId: Long) : BHumanAction(ownerId), BTargetable {

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
                        BHumanMarine.OnCreateNode.createEvent(position)
                    )
                    pipeline.pushEvent(
                        OnProduceEnableNode.createEvent(this.ownerId, this.barracksId, false)
                    )
                    return true
                }
            }
            return false
        }
    }

    class TrainMarineLvl1(ownerId: Long, barracksId: Long) : TrainMarine(ownerId, barracksId) {

        override fun isTrainConditionPerformed(unit: BUnit, player: BPlayer) =
            this.ownerId == unit.ownerId
    }

    class TrainMarineLvl2(ownerId: Long, barracksId: Long) : TrainMarine(ownerId, barracksId) {

        override fun isTrainConditionPerformed(unit: BUnit, player: BPlayer) =
            !player.isEnemy(unit.ownerId)
    }

    class TrainMarineLvl3(ownerId: Long, barracksId: Long) : TrainMarine(ownerId, barracksId) {

        override fun isTrainConditionPerformed(unit: BUnit, player: BPlayer) = true
    }

    /**
     * Node.
     */

    @BUnitComponent
    class OnCreateTrainMarineActionNode(
        context: BGameContext,
        private val ownerId: Long,
        private val barracksId: Long
    ) :
        BNode(context) {

        companion object {

            fun createTrainMarineLvl1ActionEvent(ownerId: Long, unitId: Long) =
                TrainMarineLvl1ActionEvent(ownerId, unitId)

            fun createTrainMarineLvl2ActionEvent(ownerId: Long, unitId: Long) =
                TrainMarineLvl1ActionEvent(ownerId, unitId)

            fun createTrainMarineLvl3ActionEvent(ownerId: Long, unitId: Long) =
                TrainMarineLvl1ActionEvent(ownerId, unitId)
        }

        /**
         * Creates turnTimerTask.
         */

        override fun handle(event: BEvent): BEvent? {
            if (event is TrainMarineActionEvent) {
                if (this.ownerId == event.ownerId && this.barracksId == event.unitId) {
                    val action = when (event) {
                        is TrainMarineLvl1ActionEvent -> TrainMarineLvl1(this.ownerId, this.barracksId)
                        is TrainMarineLvl2ActionEvent -> TrainMarineLvl2(this.ownerId, this.barracksId)
                        is TrainMarineLvl3ActionEvent -> TrainMarineLvl3(this.ownerId, this.barracksId)
                        else -> return null
                    }
                    val player = this.context.playerManager.getPlayerById(this.ownerId)
                    val resourceManager = player.adjutant.resourceManager
                    resourceManager.trainActions += action
                    return event
                }
            }
            return null
        }

        /**
         * Event.
         */

        abstract class TrainMarineActionEvent(val ownerId: Long, val unitId: Long) :
            BOnCreateActionPipe.OnCreateActionEvent()

        class TrainMarineLvl1ActionEvent(ownerId: Long, unitId: Long) :
            TrainMarineActionEvent(ownerId, unitId)

        class TrainMarineLvl2ActionEvent(ownerId: Long, unitId: Long) :
            TrainMarineActionEvent(ownerId, unitId)

        class TrainMarineLvl3ActionEvent(ownerId: Long, unitId: Long) :
            TrainMarineActionEvent(ownerId, unitId)
    }

    @BUnitComponent
    class OnProduceEnableNode(context: BGameContext, private val ownerId: Long, private val unitId: Long) :
        BNode(context) {

        companion object {

            fun createEvent(ownerId: Long, unitId: Long, isEnable: Boolean) =
                ProduceBarracksEnableEvent(ownerId, unitId, isEnable)
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is ProduceBarracksEnableEvent) {
                if (this.ownerId == event.ownerId && this.unitId == event.unitId) {
                    val barracks = this.context.mapManager.unitHeap[this.unitId]
                    if (barracks is BProducable) {
                        barracks.isProduceEnable = event.isEnable
                        return event
                    }
                }
            }
            return null
        }

        /**
         * Event.
         */

        open class ProduceBarracksEnableEvent(val ownerId: Long, val unitId: Long, isEnable: Boolean) :
            BOnProduceEnablePipe.ProduceEnableEvent(isEnable)
    }
}
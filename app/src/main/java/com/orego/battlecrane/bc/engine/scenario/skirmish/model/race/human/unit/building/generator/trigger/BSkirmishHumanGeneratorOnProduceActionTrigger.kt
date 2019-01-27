package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.generator.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.node.BOnProduceActionNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.engine.api.util.pipe.BParentPipe
import com.orego.battlecrane.bc.engine.standardImpl.race.human.event.BHumanConstructBuildingEvent
import com.orego.battlecrane.bc.engine.standardImpl.race.human.event.BHumanUpgradeBuildingEvent
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanGenerator

class BSkirmishHumanGeneratorOnProduceActionTrigger private constructor(
    context: BGameContext,
    val generator: BHumanGenerator
) : BNode(context) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    private val unitMap = context.storage.getHeap(BUnitHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        val producableId = this.generator.producableId
        if (event is BOnProduceActionPipe.Event
            && producableId == event.producableId
            && this.generator.isProduceEnable
        ) {
            when (event) {
                is BHumanConstructBuildingEvent -> {
                    if (event.isEnable(this.context, this.generator.playerId)) {
                        event.perform(this.context, this.generator.playerId)
                        this.pushToInnerPipes(event)
                        this.pipeline.pushEvent(BOnProduceEnablePipe.Event(producableId, false))
                        return event
                    }
                }
                is BHumanUpgradeBuildingEvent -> {
                    if (event.isEnable(this.context)) {
                        event.perform(this.pipeline)
                        this.pushToInnerPipes(event)
                        this.pipeline.pushEvent(BOnProduceEnablePipe.Event(producableId, false))
                        return event
                    }
                }
            }
        }
        return null
    }

    override fun isFinished() = !this.unitMap.containsKey(this.generator.unitId)

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BParentPipe(this)

    companion object {

        fun connect(context: BGameContext, generator: BHumanGenerator) {
            BOnProduceActionNode.connect(context) {
                BSkirmishHumanGeneratorOnProduceActionTrigger(context, generator)
            }
        }
    }
}
package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.generator.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.node.BOnProduceActionNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanGenerator
import com.orego.battlecrane.bc.engine.standardImpl.race.human.util.BHumanEvents

class BSkirmishHumanGeneratorOnProduceActionTrigger private constructor(
    context: BGameContext,
    var generator: BHumanGenerator
) : BNode(context) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    override fun handle(event: BEvent): BEvent? {
        val producableId = this.generator.producableId
        if (event is BOnProduceActionPipe.Event
            && producableId == event.producableId
            && this.generator.isProduceEnable
        ) {
            when (event) {
                is BHumanEvents.Construct.Event -> {
                    if (event.isEnable(this.context, this.generator.playerId)) {
                        event.perform(this.context, this.generator.playerId)
                        this.pushToInnerPipes(event)
                        this.pipeline.pushEvent(BOnProduceEnablePipe.createEvent(producableId, false))
                        return event
                    }
                }
                is BHumanEvents.Upgrade.Event -> {
                    if (event.isEnable(this.context)) {
                        event.perform(this.pipeline)
                        this.pushToInnerPipes(event)
                        this.pipeline.pushEvent(BOnProduceEnablePipe.createEvent(producableId, false))
                        return event
                    }
                }
            }
        }
        return null
    }

    companion object {

        fun connect(context: BGameContext, generator: BHumanGenerator) {
            BOnProduceActionNode.connect(context) {
                BSkirmishHumanGeneratorOnProduceActionTrigger(context, generator)
            }
        }
    }
}
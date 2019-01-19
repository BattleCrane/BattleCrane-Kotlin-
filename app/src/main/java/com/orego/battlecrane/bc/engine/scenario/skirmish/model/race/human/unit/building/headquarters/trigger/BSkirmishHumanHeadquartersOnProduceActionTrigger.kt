package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.headquarters.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.node.BOnProduceActionNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.standardImpl.race.human.event.BHumanConstructBuildingEvent
import com.orego.battlecrane.bc.engine.standardImpl.race.human.event.BHumanUpgradeBuildingEvent
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanHeadquarters

class BSkirmishHumanHeadquartersOnProduceActionTrigger private constructor(
    context: BGameContext,
    var headquarters: BHumanHeadquarters
) : BNode(context) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    override fun handle(event: BEvent): BEvent? {
        val producableId = this.headquarters.producableId
        if (event is BOnProduceActionPipe.Event
            && producableId == event.producableId
            && this.headquarters.isProduceEnable
        ) {
            when (event) {
                is BHumanConstructBuildingEvent -> {
                    if (event.isEnable(this.context, this.headquarters.playerId)) {
                        event.perform(this.context, this.headquarters.playerId)
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

    companion object {

        fun connect(context: BGameContext, headquarters: BHumanHeadquarters) {
            BOnProduceActionNode.connect(context) {
                BSkirmishHumanHeadquartersOnProduceActionTrigger(context, headquarters)
            }
        }
    }
}
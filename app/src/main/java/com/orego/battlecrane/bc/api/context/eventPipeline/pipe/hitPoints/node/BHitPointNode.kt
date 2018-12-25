package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.hitPoints.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent

class BHitPointNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "_NODE"
    }

    override val name = NAME

    init {
        //Put on create action node:
        //this.pipeMap[BOnCreateActionPipe.NAME] = BOnCreateActionPipe(context)
        //Put on perform action node:
        //this.pipeMap[BOnPerformActionPipe.NAME] = BOnPerformActionPipe(context)
    }

    override fun handle(event: BEvent) : BEvent? {
        //return if (event.any is BUnitPipe.UnitBundle) {
 //           this.pipeMap.values.forEach { it.push(event) }
   //         event
     //   } else {
       //     null
        //}
    }
}
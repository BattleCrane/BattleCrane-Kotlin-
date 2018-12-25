package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackAction.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackAction.BOnAttackActionPipe

class BOnAttackFinishedNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "${BOnAttackActionPipe.NAME}/ON_ATTACK_FINISHED_NODE"
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
package com.orego.battlecrane.bc.android.api.util.trigger.base

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.BTurnNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode

class BUiBaseOnTurnTrigger private constructor(private val uiGameContext: BUiGameContext) :
    BNode(uiGameContext.gameContext) {

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnTurnFinishedPipe.Event) {
            this.uiGameContext.apply {
                this.uiTaskManager.addTask {
                    this.uiClickController.currentUiClickMode = null
                    this.uiProvider.apply {
                        this.commandConstraintLayout.removeAllViews()
                        this.itemCharacteristicsConstraintLayout.removeAllViews()
                    }
                }
            }
        }
        return null
    }

    companion object {

        fun connect(uiGameContext: BUiGameContext) {
            BTurnNode.connect(uiGameContext.gameContext) {
                BUiBaseOnTurnTrigger(uiGameContext)
            }
        }
    }
}
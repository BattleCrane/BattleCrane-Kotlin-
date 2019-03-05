package com.orego.battlecrane.bc.android.api.util.trigger.base

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.heap.BUiUnitHeap
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
                    this.resetClickController()
                    this.clearUserInterface()
                    this.releaseSelectedAndActiveUnits()
                }
            }
        }
        return null
    }

    private fun BUiGameContext.resetClickController() {
        this.uiClickController.currentUiClickMode = null
    }

    private fun BUiGameContext.clearUserInterface() {
        this.uiProvider.apply {
            this.commandConstraintLayout.removeAllViews()
            this.itemCharacteristicsConstraintLayout.removeAllViews()
            this.itemDescriptionConstraintLayout.removeAllViews()
            this.leftTimeTextView.clearComposingText()
            this.itemNameTextView.clearComposingText()
        }
    }

    private fun BUiGameContext.releaseSelectedAndActiveUnits() {
        val uiUnitHeap = this.gameContext.storage.getHeap(BUiUnitHeap::class.java)
        val allUiUnits = uiUnitHeap.objectMap.values
        for (uiUnit in allUiUnits) {
            uiUnit.release(this@BUiBaseOnTurnTrigger.uiGameContext)
        }
    }

    companion object {

        fun connect(uiGameContext: BUiGameContext) {
            BTurnNode.connect(uiGameContext.gameContext) {
                BUiBaseOnTurnTrigger(uiGameContext)
            }
        }
    }
}
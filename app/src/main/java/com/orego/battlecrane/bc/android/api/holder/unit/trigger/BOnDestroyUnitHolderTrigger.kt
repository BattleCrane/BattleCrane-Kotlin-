package com.orego.battlecrane.bc.android.api.holder.unit.trigger

import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder

class BOnDestroyUnitHolderTrigger private constructor(
    private val uiContext: BUiGameContext,
    val holder : BUnitHolder
) : BNode(uiContext.gameContext) {

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnDestroyUnitPipe.Event && event.unitId == this.holder.item.unitId) {
            this.uiContext.apply {
                this.uiPipe.addAnimation {
                    this.uiProvider.mapConstraintLayout.removeView(this@BOnDestroyUnitHolderTrigger.holder.unitView)
                }
            }
        }
        return null
    }

    companion object {

        fun connect(uiContext: BUiGameContext, holder: BUnitHolder) {
            val uiTrigger = BOnDestroyUnitHolderTrigger(uiContext, holder)
            val trigger = uiContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnDestroyUnitTrigger && node.unit == holder.item
            }
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}
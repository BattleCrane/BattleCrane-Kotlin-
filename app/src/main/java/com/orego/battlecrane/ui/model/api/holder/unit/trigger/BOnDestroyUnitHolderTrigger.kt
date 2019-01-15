package com.orego.battlecrane.ui.model.api.holder.unit.trigger

import android.view.View
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.model.unit.trigger.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.std.location.grass.field.BGrassField
import com.orego.battlecrane.ui.model.api.context.BUiGameContext

class BOnDestroyUnitHolderTrigger private constructor(
    private val uiContext: BUiGameContext,
    private val emptyGrassField: BGrassField,
    private val view: View
) : BNode(uiContext.gameContext) {

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnDestroyUnitPipe.Event && event.unitId == this.emptyGrassField.unitId) {
            this.uiContext.apply {
                this.animationPipe.addAnimation {
                    this.uiProvider.mapConstraintLayout.removeView(this@BOnDestroyUnitHolderTrigger.view)
                }
            }
        }
        return null
    }

    companion object {

        fun connect(uiContext: BUiGameContext, emptyGrassField: BGrassField, view: View) {
            val uiTrigger = BOnDestroyUnitHolderTrigger(uiContext, emptyGrassField, view)
            val trigger = uiContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnDestroyUnitTrigger && node.unit == emptyGrassField
            }
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}
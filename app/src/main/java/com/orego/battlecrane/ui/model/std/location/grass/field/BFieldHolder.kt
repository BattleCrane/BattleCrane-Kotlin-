package com.orego.battlecrane.ui.model.std.location.grass.field

import android.view.View
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.std.location.grass.field.BField
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder

abstract class BFieldHolder(uiGameContext: BUiGameContext, item: BField) :
    BUnitHolder(uiGameContext, item) {

    final override val unitView: View = BUnitHolder.placeImageView(uiGameContext, item, this.getItemPath())

    init {
        OnDestroyNode.connect(uiGameContext, item, this.unitView)
    }

    protected abstract fun getItemPath() : String

    @BUnitComponent
    class OnDestroyNode(
        private val uiContext: BUiGameContext,
        private val emptyField: BField,
        private val view: View
    ) : BNode(uiContext.gameContext) {

        companion object {

            fun connect(uiContext: BUiGameContext, emptyField: BField, view: View) {
                val onDestroyNode = OnDestroyNode(uiContext, emptyField, view)
                val pipeId = emptyField.destroyConnection.sourcePipeId
                uiContext.gameContext.pipeline.bindNodeToPipe(pipeId, onDestroyNode)
            }
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnDestroyUnitPipe.Event && event.unitId == this.emptyField.unitId) {
                this.uiContext.apply {
                    this.animationPipe.addAnimation {
                        this.uiProvider.mapConstraintLayout.removeView(this@OnDestroyNode.view)
                    }
                }
            }
            return null
        }
    }
}
package com.orego.battlecrane.ui.model.std.ground.field.empty

import android.view.View
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder

class BEmptyFieldHolder(uiGameContext: BUiGameContext, emptyField: BEmptyField) :
    BUnitHolder(uiGameContext.gameContext, emptyField) {

    companion object {

        private const val PATH = "std/grass/unit/empty_field.png"
    }

    override val unitView: View = BUnitHolder.createImageView(uiGameContext, emptyField, PATH)

    init {
        OnDestroyNode.connect(uiGameContext, emptyField, this.unitView)
    }

    @BUnitComponent
    class OnDestroyNode(
        private val uiContext: BUiGameContext,
        private val emptyField: BEmptyField,
        private val view: View
    ) : BNode(uiContext.gameContext) {

        companion object {

            fun connect(uiContext: BUiGameContext, emptyField: BEmptyField, view: View) {
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
package com.orego.battlecrane.ui.model.std.ground.field.empty

import android.view.View
import android.widget.ImageView
import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.ui.fragment.battle.BBattleFragment
import com.orego.battlecrane.ui.model.api.shell.item.supplier.BStandardUnitSupplier

class BUiEmptyFieldSupplier : BStandardUnitSupplier() {

    companion object {

        private const val PATH = "std/grass/unit/empty_field.png"
    }

    override val itemClassName: String = BEmptyField::class.java.name

    override val path: String = PATH

    override fun supply(uiContext: BBattleFragment.Presenter.BUiGameContext, item: BUnit, imageView: ImageView) {
        if (item is BEmptyField) {
            OnDestroyNode.connect(uiContext, item, imageView)
        }
    }

    @BUnitComponent
    class OnDestroyNode(
        private val uiContext: BBattleFragment.Presenter.BUiGameContext,
        private val emptyField: BEmptyField,
        private val view: View
    ) : BNode(uiContext.gameContext) {

        companion object {

            fun connect(uiContext: BBattleFragment.Presenter.BUiGameContext, emptyField: BEmptyField, view: View) {
                val onDestroyNode = OnDestroyNode(uiContext, emptyField, view)
                val pipeId = emptyField.destroyConnection.sourcePipeId
                uiContext.gameContext.pipeline.bindNodeToPipe(pipeId, onDestroyNode)
            }
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnDestroyUnitPipe.Event && event.unitId == this.emptyField.unitId) {
                this.uiContext.
            }
        }
    }
}
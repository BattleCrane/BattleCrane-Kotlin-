package com.orego.battlecrane.ui.model.api.uiGameContext

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.ui.model.api.component.context.BUiContextComponent
import com.orego.battlecrane.ui.model.api.render.BViewRender
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRender
import com.orego.battlecrane.ui.model.api.scenarioMaterials.BUiScenarioMaterials
import com.orego.battlecrane.ui.model.api.uiGameContext.clickController.BClickController
import com.orego.battlecrane.ui.model.api.uiGameContext.eventPipe.BUiEventPipe

@BUiContextComponent
class BUiGameContext(
    val applicationContext: Context,
    val gameContext: BGameContext,
    val materials: BUiScenarioMaterials
) {

    @BUiContextComponent
    val eventPipe = BUiEventPipe(this.gameContext)

    @BUiContextComponent
    val clickController = BClickController()

    fun initMap(surface: ConstraintLayout, factory: BUnitViewFactory) {
        val units = this.gameContext.storage.getHeap(BUnitHeap::class.java).getObjectList()
        for (unit in units) {
            factory.build(this, surface, unit)
        }
    }

    fun init





















    interface BUnitViewBuilder<K : Any, V : Any> {

        val type: String

        fun build(value: K, dimension: Int, context: Context): V
    }

    open class ViewFactory<K : Any, V : Any> {

        private val builderMap = mutableMapOf<String, BViewRender.ViewBuilder<K, V>>()

        fun addBuilder(builder: BViewRender.ViewBuilder<K, V>) {
            val type = builder.type
            val builderMap = this.builderMap
            if (!builderMap.containsKey(type)) {
                builderMap[type] = builder
            } else {
                throw IllegalStateException("The type is added")
            }
        }

        fun build(unit: K, dimension: Int, context: Context, type: String): V {
            val builder = this.builderMap[type]
            return builder?.build(unit, dimension, context) ?: this.buildByDefault(unit, dimension, context, type)
        }

        open fun buildByDefault(unit: K, dimension: Int, context: Context, type: String): V {
            throw IllegalStateException("Not supported type!")
        }
    }
}
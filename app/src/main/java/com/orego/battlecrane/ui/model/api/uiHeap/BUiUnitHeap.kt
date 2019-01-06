package com.orego.battlecrane.ui.model.api.uiHeap

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.ui.model.api.component.context.BUiContextComponent
import com.orego.battlecrane.ui.model.api.render.BViewRender
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRender
import com.orego.battlecrane.ui.model.api.view.unit.BUnitView

@BUiContextComponent
class BUiUnitHeap(private val applicationContext: Context) {

    lateinit var factory: BViewRender.ViewFactory<BUnit, BUnitView>

    val units = mutableMapOf<Long, BUnitView>()

    fun init(context: Context, units : List<BUnit>) {
        for (unit in units) {
            val unitView = this.factory.build(context, unit, unit::class.java.name)
            this.units[unit.unitId] = unitView
        }
    }
}
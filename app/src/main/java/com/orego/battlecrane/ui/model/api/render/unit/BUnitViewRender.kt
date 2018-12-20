package com.orego.battlecrane.ui.model.api.render.unit

import com.orego.battlecrane.bc.api.context.mapManager.BMapManager.MAP_SIZE
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.ui.model.api.render.BViewRender
import com.orego.battlecrane.ui.model.api.view.unit.BUnitView
import com.orego.battlecrane.ui.util.addView
import com.orego.battlecrane.ui.util.moveTo

class BUnitViewRender(private val units: Map<Long, BUnit>) : BViewRender<BUnit, BUnitView>() {

    override fun draw() {
        //Map relation is 1:1:
        val dimensionX = this.constraintLayout.measuredWidth / MAP_SIZE
        val dimensionY = this.constraintLayout.measuredHeight / MAP_SIZE

        println("DIM X: $dimensionX")
        println("DIM Y: $dimensionY")

        val constraintLayoutId = this.constraintLayout.id
        //Draw units:
        for (unit in this.units.values) {
            val type = unit::class.java.name
            val unitView = this.factory.build(unit, dimensionX - 3, this.context, type)
            this.constraintLayout.addView(unitView)
            this.temporaryViewList.add(unitView)
        }
        this.constraintSet.clone(this.constraintLayout)
        //Move units:
        for (view in this.temporaryViewList) {
            val displayedViewId = view.displayedView.id
            val pivot = view.entity.pivot!!
            val x = pivot.x * dimensionX
            val y = pivot.y * dimensionY
            this.constraintSet.moveTo(displayedViewId, constraintLayoutId, x, y)
        }
        this.constraintSet.applyTo(this.constraintLayout)
        this.temporaryViewList.clear()
    }

    class ViewFactory : BViewRender.ViewFactory<BUnit, BUnitView>()

    interface ViewBuilder : BViewRender.ViewBuilder<BUnit, BUnitView>
}
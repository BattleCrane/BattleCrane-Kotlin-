package com.orego.battlecrane.ui.model.api.render.unit

import com.orego.battlecrane.bc.api.context.mapManager.BMapManager.MAP_SIZE
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.ui.model.api.render.BViewRender
import com.orego.battlecrane.ui.model.api.view.unit.BUnitView
import com.orego.battlecrane.ui.util.addView
import com.orego.battlecrane.ui.util.moveTo
import org.intellij.lang.annotations.MagicConstant

/**
 * @author StarKRE 20.12.2018.
 * Draw units on constraint layout.
 */

class BUnitViewRender(private val units: Map<Long, BUnit>) : BViewRender<BUnit, BUnitView>() {

    companion object {

        @MagicConstant
        private const val PADDING_VALUE = 3
    }

    /**
     * Current ui unit heap.
     */

    private val actualUnitViewMap = mutableMapOf<Long, BUnitView>()

    /**
     * Added only NEW & remove only OLD unit views.
     */

    override fun draw() {
        //Map relation is 1:1:
        val dimensionX = this.constraintLayout.measuredWidth / MAP_SIZE
        val dimensionY = this.constraintLayout.measuredHeight / MAP_SIZE
        val constraintLayoutId = this.constraintLayout.id
        //Collect added & removed views:
        val addedUnitIdSet = mutableSetOf<Long>()
        val removedUnitIdSet = mutableSetOf<Long>()
        for (unitEntry in this.units) {
            val key = unitEntry.key
            if (!this.actualUnitViewMap.containsKey(key)) {
                addedUnitIdSet.add(key)
            }
        }
        for (actualId in this.actualUnitViewMap.keys) {
            if (!this.units.containsKey(actualId)) {
                removedUnitIdSet.add(actualId)
            }
        }
        //Remove units from constraint layout:
        for (unitId in removedUnitIdSet){
            val unitView = this.actualUnitViewMap[unitId]!!
            this.constraintLayout.removeView(unitView.displayedView)
            this.actualUnitViewMap.remove(unitId)
        }
        //Add units to constraint layout:
        for (unitId in addedUnitIdSet) {
            val unit = this.units[unitId]!!
            val type = unit::class.java.name
            val unitView = this.factory.build(unit, dimensionX - PADDING_VALUE, this.context, type)
            this.constraintLayout.addView(unitView)
            this.actualUnitViewMap[unitId] = unitView
        }
        //Move units to positions:
        this.constraintSet.clone(this.constraintLayout)
        //Move units:
        for (unitId in addedUnitIdSet) {
            val unit = this.actualUnitViewMap[unitId]!!
            val displayedViewId = unit.displayedView.id
            val pivot = unit.entity.pivot!!
            val x = pivot.x * dimensionX
            val y = pivot.y * dimensionY
            this.constraintSet.moveTo(displayedViewId, constraintLayoutId, x, y)
        }
        //Apply changes to constraint layout:
        this.constraintSet.applyTo(this.constraintLayout)
    }

    /**
     * Factory.
     */

    class ViewFactory : BViewRender.ViewFactory<BUnit, BUnitView>()

    /**
     * Unit builder.
     */

    interface ViewBuilder : BViewRender.ViewBuilder<BUnit, BUnitView>
}
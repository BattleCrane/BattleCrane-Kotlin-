package com.orego.battlecrane.bc.android.api.model.unit.factory

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

/**
 * Creates a ui unit by builder map.
 */

class BUiUnitFactory {

    val renderFunctionMap: MutableMap<Class<out BUnit>, (BUiGameContext, BUnit) -> BUiUnit> =
        mutableMapOf()

    fun build(uiGameContext: BUiGameContext, item: BUnit): BUiUnit {
        //Get unit clazz:
        val unitClazz = item::class.java
        //Find function for creating of ui unit:
        val createUiUnit = this.renderFunctionMap[unitClazz]!!
        //Create unit:
        val uiUnit = createUiUnit(uiGameContext, item)
        //Draw unit after creating:
        val unitView = uiUnit.createView(uiGameContext)
        //Put in the storage:
        uiGameContext.gameContext.storage.putObject(uiUnit)
        //Put on the map:
        uiGameContext.uiProvider.mapConstraintLayout.addView(unitView)
        return uiUnit
    }
}
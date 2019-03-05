package com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BUiHumanAssets
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanFactory

class BUiHumanFactory private constructor(uiGameContext: BUiGameContext, override val unit: BHumanFactory) :
    BUiUnit(uiGameContext, unit) {

    companion object {

        const val PATH = "${BUiHumanAssets.Unit.Building.PATH}/factory"
    }

    override fun createPath() : String {
        val viewKey =  this.viewMode.key
        val level = this.unit.currentLevel
        val hitPoints = this.unit.currentHitPoints
        return "$PATH/$viewKey/${level}_$hitPoints.png"
    }

    open class Builder(override val unit: BHumanFactory) : BUiUnit.Builder(unit) {

        override fun onCreate(uiGameContext: BUiGameContext) =
            BUiHumanFactory(uiGameContext, this.unit)
    }
}
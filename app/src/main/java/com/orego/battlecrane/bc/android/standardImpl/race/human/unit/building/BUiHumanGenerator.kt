package com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BUiHumanAssets
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanGenerator

class BUiHumanGenerator private constructor(uiGameContext: BUiGameContext, override val unit: BHumanGenerator) :
    BUiUnit(uiGameContext, unit) {

    companion object {

        const val PATH = "${BUiHumanAssets.Unit.Building.PATH}/generator"
    }

    override fun createPath(): String {
        val viewKey = this.viewMode.key
        val level = this.unit.currentLevel
        val hitPoints = this.unit.currentHitPoints
        return "$PATH/$viewKey/${level}_$hitPoints.png"
    }

    /**
     * Builder.
     */

    open class Builder(override val unit: BHumanGenerator) : BUiUnit.Builder(unit) {

        override fun onCreate(uiGameContext: BUiGameContext) =
            BUiHumanGenerator(uiGameContext, this.unit)
    }
}
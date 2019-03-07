package com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BUiHumanAssets
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanTurret

class BUiHumanTurret private constructor(uiGameContext: BUiGameContext, override val unit: BHumanTurret) :
    BUiUnit(uiGameContext, unit) {

    companion object {

        const val PATH = "${BUiHumanAssets.Unit.Building.PATH}/turret"
    }

    override fun getAssetPath(): String {
        val viewKey = this.viewMode.key
        val level = this.unit.currentLevel
        val hitPoints = this.unit.currentHitPoints
        return "$PATH/$viewKey/${level}_$hitPoints.png"
    }

    /**
     * Builder.
     */

    open class Builder(override val unit: BHumanTurret) : BUiUnit.Builder(unit) {

        override fun onCreate(uiGameContext: BUiGameContext): BUiHumanTurret {
            return BUiHumanTurret(uiGameContext, this.unit)
        }
    }
}
package com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building

import com.orego.battlecrane.bc.android.api.asset.BUiAssets
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BUiHumanAssets
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanWall

class BUiHumanWall private constructor(uiGameContext: BUiGameContext, override val unit: BHumanWall) :
    BUiUnit(uiGameContext, unit) {

    companion object {

        const val PATH = "${BUiHumanAssets.Unit.Building.PATH}/wall"
    }

    override fun getItemPath(): String {
        val playerId = this.unit.playerId
        val viewKey = BUiAssets.ViewMode.getKeyByPlayerId(playerId)
        val hitPoints = this.unit.currentHitPoints
        return "$PATH/$viewKey/$hitPoints.png"
    }

    /**
     * Builder.
     */

    open class Builder(override val unit: BHumanWall) : BUiUnit.Builder(unit) {

        override fun onCreate(uiGameContext: BUiGameContext) =
            BUiHumanWall(uiGameContext, this.unit)
    }
}
package com.orego.battlecrane.bc.android.standardImpl.race.human.unit.infantry

import com.orego.battlecrane.bc.android.api.asset.BUiAssets
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BUiHumanAssets
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.infantry.implementation.BHumanMarine

class BUiHumanMarine private constructor(uiGameContext: BUiGameContext, override val unit: BHumanMarine) :
    BUiUnit(uiGameContext, unit) {

    companion object {

        const val PATH = "${BUiHumanAssets.Unit.Infantry.PATH}/marine"
    }

    override fun getItemPath() : String {
        val playerId = this.unit.playerId
        val viewKey =  BUiAssets.ViewMode.getKeyByPlayerId(playerId)
        return "$PATH/$viewKey.png"
    }

    /**
     * Builder.
     */

    open class Builder(override val unit: BHumanMarine) : BUiUnit.Builder(unit) {

        override fun onCreate(uiGameContext: BUiGameContext) =
            BUiHumanMarine(uiGameContext, this.unit)
    }
}
package com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building

import com.orego.battlecrane.bc.android.api.asset.BUiAsset
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BUiHumanAssets
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanBarracks

class BUiHumanBarracks private constructor(uiGameContext: BUiGameContext, override val item: BHumanBarracks) :
    BUiUnit(uiGameContext, item) {

    companion object {

        const val PATH = "${BUiHumanAssets.UNIT}/barracks"
    }

    override fun getItemPath() =
        "$PATH/" +
                "${BUiAsset.COLOR_MAP[this.item.playerId]}/" +
                "${this.item.currentLevel}_${this.item.currentHitPoints}.png"

    /**
     * Builder.
     */

    open class Builder : BUiUnit.Builder() {

        override fun onCreate(uiGameContext: BUiGameContext, item: BUnit) =
            BUiHumanBarracks(uiGameContext, item as BHumanBarracks)
    }
}
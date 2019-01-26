package com.orego.battlecrane.bc.android.standardImpl.race.human.unit.vehicle

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BClickMode
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.vehicle.implementation.BHumanTank

class BHumanTankHolder private constructor(uiGameContext: BUiGameContext, override val item: BHumanTank) :
    BUnitHolder(uiGameContext, item) {

    companion object {

        private val COLOR_MAP = mapOf(
            1.toLong() to "blue",
            2.toLong() to "red"
        )
    }

    override val unitView = BUnitHolder.placeImageView(uiGameContext, this.item, this.getItemPath())

    init {
        this.unitView.setOnClickListener {
            uiGameContext.clickController.pushClickMode(ClickMode())
        }
    }

    fun getItemPath() =
        "race/human/unit/tank/" +
                "${COLOR_MAP[this.item.playerId]}/" +
                "${this.item.currentHitPoints}_${
                if (this.item.isAttackEnable) {
                    "active"
                } else {
                    "passive"
                }
                }.png"

    /**
     * Click mode.
     */

    inner class ClickMode : BUnitHolder.ClickMode(this) {

        override fun onStart() {
            println("Show description!!!")
        }

        override fun onNext(nextClickMode: BClickMode) = nextClickMode.also { it.onStart() }
    }

    /**
     * Builder.
     */

    open class Builder : BUnitHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit) =
            BHumanTankHolder(uiGameContext, item as BHumanTank)
    }
}
package com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BClickMode
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanTurret

class BHumanTurretHolder private constructor(uiGameContext: BUiGameContext, override val item: BHumanTurret) :
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
        "race/human/unit/turret/" +
                "${COLOR_MAP[this.item.playerId]}/" +
                "${this.item.currentLevel}_${this.item.currentHitPoints}.png"



    /**
     * Click mode.
     */

    inner class ClickMode : BUnitHolder.ClickMode(this) {

        override fun onStart() {
            println("Show description!!!")
        }

        override fun onNext(nextClickMode: BClickMode) = nextClickMode.also { it.onStart() }
    }

    open class Builder : BUnitHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit): BHumanTurretHolder {
            return BHumanTurretHolder(uiGameContext, item as BHumanTurret)
        }
    }
}
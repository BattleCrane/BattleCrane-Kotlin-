package com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode
import com.orego.battlecrane.bc.android.api.model.unit.BUnitHolder
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanGenerator

class BHumanGeneratorHolder private constructor(uiGameContext: BUiGameContext, override val item: BHumanGenerator) :
    BUnitHolder(uiGameContext, item) {


    override val unitView = BUnitHolder.placeImageView(uiGameContext, this.item, this.getItemPath())

    companion object {

        private val COLOR_MAP = mapOf(
            1.toLong() to "blue",
            2.toLong() to "red"
        )
    }

    init {
        this.unitView.setOnClickListener {
            uiGameContext.uiClickController.pushClickMode(UiClickMode())
        }
    }


    fun getItemPath() =
        "race/human/unit/generator/" +
                "${COLOR_MAP[this.item.playerId]}/" +
                "${this.item.currentLevel}_${this.item.currentHitPoints}.png"



    /**
     * Click mode.
     */

    inner class UiClickMode : BUnitHolder.UiClickMode(this) {

        override fun onStartClickMode() {
            println("Show description!!!")
        }

        override fun onNextClickMode(nextUiClickMode: BUiClickMode) = nextUiClickMode.also { it.onStartClickMode() }
    }

    open class Builder : BUnitHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit) =
            BHumanGeneratorHolder(uiGameContext, item as BHumanGenerator)
    }
}
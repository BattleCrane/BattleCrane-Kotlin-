package com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BClickMode
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanFactory

class BHumanFactoryHolder private constructor(uiGameContext: BUiGameContext, override val item: BHumanFactory) :
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
            uiGameContext.clickController.pushClickMode(ClickMode())
        }
    }


    fun getItemPath() =
        "race/human/unit/factory/" +
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

        override fun build(uiGameContext: BUiGameContext, item: BUnit) =
            BHumanFactoryHolder(uiGameContext, item as BHumanFactory)
    }
}
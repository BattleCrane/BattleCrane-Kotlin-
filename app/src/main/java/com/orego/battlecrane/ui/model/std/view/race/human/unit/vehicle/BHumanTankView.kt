package com.orego.battlecrane.ui.model.std.view.race.human.unit.vehicle

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.unit.vehicle.implementation.BHumanTank
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRenderItem
import com.orego.battlecrane.ui.model.api.view.unit.BUnitView
import com.orego.battlecrane.ui.util.byAssets
import com.orego.battlecrane.ui.util.setImageByAssets

class BHumanTankView(
    override val entity: BHumanTank, dimension: Int, private val context: Context
) : BUnitView(entity) {

    override val displayedView: ImageView

    companion object {

        private val COLOR_MAP = mapOf(
            1.toLong() to "blue",
            2.toLong() to "red"
        )
    }

    init {
        val width = dimension * this.entity.width
        val height = dimension * this.entity.height
        this.displayedView = ImageView(context).byAssets(
            context, width, height, this.getPath()
        )
    }

    private fun getPath() =
        "race/human/unit/tank/" +
                "${COLOR_MAP[this.entity.playerId!!.id]}/" +
                "${this.entity.currentHitPoints}_${
                if (this.entity.isAttackEnable) {
                    "active"
                } else {
                    "passive"
                }
                }.png"

    override fun refresh() {
        this.displayedView.setImageByAssets(this.context, this.getPath())
    }
    
    class Builder : BUnitViewRenderItem.ViewBuilder {

        override val type: String = BHumanTank::class.java.name

        override fun build(value: BUnit, dimension: Int, context: Context): BUnitView =
            BHumanTankView(value as BHumanTank, dimension, context)
    }
}
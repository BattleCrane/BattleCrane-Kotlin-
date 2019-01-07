package com.orego.battlecrane.ui.model.std.race.human.unit.building

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanTurret
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRenderItem
import com.orego.battlecrane.ui.model.api.view.unit.BUnitView
import com.orego.battlecrane.ui.util.byAssets
import com.orego.battlecrane.ui.util.setImageByAssets

class BHumanTurretView(
    override val entity: BHumanTurret, dimension: Int, private val context: Context
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
        "race/human/unit/turret/" +
                "${COLOR_MAP[this.entity.playerId!!.id]}/" +
                "${this.entity.currentLevel}_${this.entity.currentHitPoints}.png"

    override fun refresh() {
        this.displayedView.setImageByAssets(this.context, this.getPath())
    }

    class Builder : BUnitViewRenderItem.ViewBuilder {

        override val type: String = BHumanTurret::class.java.name

        override fun build(value: BUnit, dimension: Int, context: Context): BUnitView =
            BHumanTurretView(value as BHumanTurret, dimension, context)
    }
}
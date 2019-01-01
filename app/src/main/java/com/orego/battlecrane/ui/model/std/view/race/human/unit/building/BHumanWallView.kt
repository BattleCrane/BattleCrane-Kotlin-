package com.orego.battlecrane.ui.model.std.view.race.human.unit.building

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.bc.api.model.entity.main.BUnit
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanWall
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRender
import com.orego.battlecrane.ui.model.api.view.unit.BUnitView
import com.orego.battlecrane.ui.util.byAssets
import com.orego.battlecrane.ui.util.setImageByAssets

class BHumanWallView(
    override val entity: BHumanWall, dimension: Int, private val context: Context
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
        "race/human/unit/wall/" +
                "${COLOR_MAP[this.entity.playerId!!.id]}/" +
                "${this.entity.currentHitPoints}.png"

    override fun refresh() {
        this.displayedView.setImageByAssets(this.context, this.getPath())
    }

    class Builder : BUnitViewRender.ViewBuilder {

        override val type: String = BHumanWall::class.java.name

        override fun build(value: BUnit, dimension: Int, context: Context): BUnitView =
            BHumanWallView(value as BHumanWall, dimension, context)
    }
}
package com.orego.battlecrane.ui.model.std.view.race.human.unit.building

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.bc.api.model.contract.BUnit
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanBarracks
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRender
import com.orego.battlecrane.ui.model.api.view.unit.BUnitView
import com.orego.battlecrane.ui.util.byAssets
import com.orego.battlecrane.ui.util.setImageByAssets

class BHumanBarracksView(
    override val entity: BHumanBarracks, dimension: Int, private val context: Context
) : BUnitView(entity) {

    companion object {

        private val COLOR_MAP = mapOf(
            1.toLong() to "blue",
            2.toLong() to "red"
        )
    }

    override val displayedView: ImageView

    init {
        val width = dimension * this.entity.horizontalSize
        val height = dimension * this.entity.verticalSize
        this.displayedView = ImageView(context).byAssets(
            context, width, height, this.getPath()
        )
    }

    private fun getPath() =
        "race/human/unit/barracks/" +
                "${COLOR_MAP[this.entity.ownerId!!.id]}/" +
                "${this.entity.currentLevel}_${this.entity.currentHitPoints}.png"

    override fun refresh() {
        this.displayedView.setImageByAssets(this.context, this.getPath())
    }

    class Builder : BUnitViewRender.ViewBuilder {

        override val type: String = BHumanBarracks::class.java.name

        override fun build(value: BUnit, dimension: Int, context: Context): BUnitView =
            BHumanBarracksView(value as BHumanBarracks, dimension, context)
    }
}


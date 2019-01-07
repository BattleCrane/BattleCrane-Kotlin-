package com.orego.battlecrane.ui.model.std.race.human.unit.building

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanHeadquarters
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRenderItem
import com.orego.battlecrane.ui.model.api.view.unit.BUnitView
import com.orego.battlecrane.ui.util.byAssets
import com.orego.battlecrane.ui.util.setImageByAssets

class BHumanHeadquartersView(
    override val entity: BHumanHeadquarters, dimension: Int, private val context: Context
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
        this.displayedView = ImageView(this.context).byAssets(
            this.context, width, height, this.getPath()
        )
    }

    private fun getPath() =
        "race/human/unit/headquarters/" +
                "${COLOR_MAP[this.entity.playerId!!.id]}/" +
                "${this.entity.currentHitPoints}.png"

    override fun refresh() {
        this.displayedView.setImageByAssets(this.context, this.getPath())
    }

    class Builder : BUnitViewRenderItem.ViewBuilder {

        override val type: String = BHumanHeadquarters::class.java.name

        override fun build(value: BUnit, dimension: Int, context: Context): BUnitView =
            BHumanHeadquartersView(value as BHumanHeadquarters, dimension, context)
    }
}
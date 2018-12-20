package com.orego.battlecrane.ui.model.std.view.race.human.unit.building

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanBarracks
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRender
import com.orego.battlecrane.ui.model.api.view.map.BUnitView
import com.orego.battlecrane.ui.util.byResource
import com.orego.battlecrane.ui.util.setImageById

class BHumanBarracksView(
    override val entity: BHumanBarracks, dimension: Int, private val context: Context
) : BUnitView(entity) {

    override val displayedView: ImageView

    private val imageMatrix = listOf<List<List<Int>>>()

    init {
        this.displayedView = ImageView(context).byResource(
            context, dimension, this.getImageId()
        )
    }

    private fun getImageId() =
        this.imageMatrix[this.entity.owner!!.id as Int][this.entity.currentLevel - 1][this.entity.currentHitPoints - 1]

    override fun refresh() {
        this.displayedView.setImageById(this.context, this.getImageId())
    }

    class Builder : BUnitViewRender.ViewBuilder {

        override val type: String = BHumanBarracks::class.java.name

        override fun build(value: BUnit, dimension: Int, context: Context): BUnitView =
            BHumanBarracksView(value as BHumanBarracks, dimension, context)
    }
}


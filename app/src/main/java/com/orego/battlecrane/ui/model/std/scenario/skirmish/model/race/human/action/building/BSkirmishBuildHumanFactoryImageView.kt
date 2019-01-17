package com.orego.battlecrane.ui.model.std.scenario.skirmish.model.race.human.action.building

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.std.location.grass.field.implementation.empty.BEmptyGrassField
import com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.event.BSkirmishHumanEvents
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.context.clickController.BClickMode
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder

object BSkirmishBuildHumanFactoryImageView {

    const val PATH = "race/human/action/build_factiory.png"

    fun create(uiGameContext: BUiGameContext, producableId: Long, playerId: Long): ImageView {
        val applicationContext = uiGameContext.uiProvider.applicationContext
        return ImageView(applicationContext)
            .also {
                val imageStream = applicationContext.assets.open(PATH)
                val drawable = Drawable.createFromStream(imageStream, null)
                it.id = View.generateViewId()
                it.setImageDrawable(drawable)
                it.setOnClickListener {
                    selectBuildMode(
                        uiGameContext,
                        producableId,
                        playerId
                    )
                }
            }
    }

    private fun selectBuildMode(uiGameContext: BUiGameContext, producableId: Long, playerId: Long) {
        uiGameContext.clickController.forceChangeClickMode(
            ClickMode(
                uiGameContext.gameContext,
                producableId,
                playerId
            )
        )
        println("CHANGED MODE!!!")
    }

    /**
     * Click mode.
     */

    private class ClickMode(
        private val gameContext: BGameContext,
        private val producableId: Long,
        private val playerId: Long
    ) : BClickMode {

        override fun finish(nextClickMode: BClickMode): Boolean {
            if (nextClickMode is BUnitHolder.ClickMode) {
                val clickedUnit = nextClickMode.unitHolder.item
                if (clickedUnit is BEmptyGrassField) {
                    val event = BSkirmishHumanEvents.Construct.BarracksEvent(this.producableId, clickedUnit.x, clickedUnit.y)
                    val isSuccessful = event.isEnable(this.gameContext, this.playerId)
                    if (isSuccessful) {
                        this.gameContext.pipeline.broacastEvent(event)
                    }
                    println("BUILD BARRACKS!!!")
                    return isSuccessful
                }
            }
            return false
        }
    }
}
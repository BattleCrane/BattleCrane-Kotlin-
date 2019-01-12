package com.orego.battlecrane.ui.model.std.race.human.action.building

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.util.BHumanEvents
import com.orego.battlecrane.ui.model.api.clickController.BClickMode
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder

object BHumanBuildBarracksImageView {

    const val PATH = "race/human/action/build_barracks.png"

    fun create(uiGameContext: BUiGameContext, producableId: Long, playerId: Long): ImageView {
        val applicationContext = uiGameContext.uiProvider.applicationContext
        return ImageView(applicationContext)
            .also {
                val imageStream = applicationContext.assets.open(PATH)
                val drawable = Drawable.createFromStream(imageStream, null)
                it.id = View.generateViewId()
                it.setImageDrawable(drawable)
                it.setOnClickListener { this.selectBuildBarracksMode(uiGameContext, producableId, playerId) }
            }
    }

    private fun selectBuildBarracksMode(uiGameContext: BUiGameContext, producableId: Long, playerId: Long) {
        uiGameContext.clickController.forceChangeClickMode(
            ClickMode(uiGameContext.gameContext, producableId, playerId)
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
                if (clickedUnit is BEmptyField) {
                    val event = BHumanEvents.Construct.BarracksEvent(producableId, clickedUnit.x, clickedUnit.y)
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
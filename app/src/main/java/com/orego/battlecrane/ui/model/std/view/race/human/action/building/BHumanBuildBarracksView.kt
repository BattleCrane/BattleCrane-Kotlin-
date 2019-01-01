package com.orego.battlecrane.ui.model.std.view.race.human.action.building

import android.content.Context
import android.widget.ImageView
import com.orego.battlecrane.bc.api.model.entity.main.BAction
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanHeadquarters
import com.orego.battlecrane.ui.model.api.modeController.BClickMode
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.view.action.BActionView
import com.orego.battlecrane.ui.model.api.view.unit.BUnitView
import com.orego.battlecrane.ui.util.byAssets

class BHumanBuildBarracksView(
    override val entity: BHumanHeadquarters.BuildBarracksFactory.Action, dimension: Int, context: Context
) : BActionView(entity) {

    companion object {

        private const val PATH = "race/human/action/build_barracks.png"
    }

    override val displayedView = ImageView(context).also {
        it.byAssets(context, dimension, dimension, PATH)
        it.setOnClickListener { this.selectBuildBarracksMode() }
    }

    private fun selectBuildBarracksMode() {
        this.clickController.forceChangeClickMode(ClickMode())
        println("CHANGED MODE!!!")
    }

    /**
     * Click mode.
     */

    private inner class ClickMode : BActionView.ClickMode() {

        override fun finish(otherClickMode: BClickMode): Boolean {
            if (otherClickMode is BUnitView.ClickMode) {
                val clickedUnit = otherClickMode.getUnit()
                if (clickedUnit is BEmptyField) {
                    val action = this@BHumanBuildBarracksView.entity
                    action.targetPosition = clickedUnit.pivot!!
                    val isSuccessful = action.perform()
                    if (isSuccessful) {
                        println("YES!!!")
                        //TODO: NEED FRAGMENT AS INVERSION CONTROL!!!
                        this.unitRender.draw()

                    }
                    println("BUILD BARRACKS!!!")
                    return isSuccessful
                }
            }
            return false
        }
    }

    /**
     * Builder.
     */

    class Builder : BActionViewRender.ViewBuilder {

        override fun build(value: BAction, dimension: Int, context: Context) =
            BHumanBuildBarracksView(
                value as BHumanHeadquarters.BuildBarracksFactory.Action, dimension, context
            )

        override val type: String =
            BHumanHeadquarters.BuildBarracksFactory.Action::class.java.name
    }
}
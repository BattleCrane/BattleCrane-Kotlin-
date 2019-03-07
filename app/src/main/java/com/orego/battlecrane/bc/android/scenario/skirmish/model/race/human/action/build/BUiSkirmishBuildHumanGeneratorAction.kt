package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.action.build

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode
import com.orego.battlecrane.bc.android.api.model.action.BUiAction
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BUiHumanAssets
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.unit.type.BEmptyField
import com.orego.battlecrane.bc.engine.api.model.util.BProducable
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.event.construct.BSkirmishHumanConstructGeneratorEvent
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.utils.BSkirmishHumanRule
import com.orego.battlecrane.bc.engine.standardImpl.race.human.util.BHumanCalculations

class BUiSkirmishBuildHumanGeneratorAction(uiGameContext: BUiGameContext, private val producable: BProducable) :
    BUiAction(uiGameContext) {

    companion object {

        const val PATH = "${BUiHumanAssets.Action.Build.PATH}/generator"
    }

    override val uiClickMode by lazy {
        UiClickMode(uiGameContext)
    }

    /**
     * Update.
     */

    override fun createPath(): String {
        val viewKey = this.viewMode.key
        return "$PATH/$viewKey.png"
    }

    /**
     * Active.
     */

    override fun canActivate(uiGameContext: BUiGameContext): Boolean {
        val context = uiGameContext.gameContext
        val playerId = this.producable.playerId
        val generatorCount = BHumanCalculations.countGenerators(context, playerId)
        return this.producable.isProduceEnable && generatorCount < BSkirmishHumanRule.GENERATOR_LIMIT
    }

    /**
     * Select.
     */

    override fun onSelect(uiGameContext: BUiGameContext) {
    }

    override fun showDescription(uiGameContext: BUiGameContext) {
    }

    /**
     * Dismiss.
     */

    override fun onPerform(uiGameContext: BUiGameContext) {
        this.dismiss(uiGameContext)
    }

    /**
     * Click mode.
     */

    inner class UiClickMode(uiGameContext: BUiGameContext) : BUiAction.UiClickMode(uiGameContext, this) {

        private val producable = this@BUiSkirmishBuildHumanGeneratorAction.producable

        private val gameContext: BGameContext = uiGameContext.gameContext

        override fun onNextClickMode(nextUiClickMode: BUiClickMode?): BUiClickMode? {
            if (nextUiClickMode is BUiUnit.UiClickMode) {
                val clickedUnit = nextUiClickMode.uiUnit.unit
                if (clickedUnit is BEmptyField) {
                    val playerId = this.producable.playerId
                    val producableId = this.producable.producableId
                    val x = clickedUnit.x
                    val y = clickedUnit.y
                    val event = BSkirmishHumanConstructGeneratorEvent(producableId, x, y)
                    val isSuccessful = event.isEnable(this.gameContext, playerId)
                    if (isSuccessful) {
                        this.gameContext.pipeline.broacastEvent(event)
                        this.action.onPerform(this.uiGameContext)
                        return null
                    }
                }
            }
            return super.onNextClickMode(nextUiClickMode)
        }
    }
}
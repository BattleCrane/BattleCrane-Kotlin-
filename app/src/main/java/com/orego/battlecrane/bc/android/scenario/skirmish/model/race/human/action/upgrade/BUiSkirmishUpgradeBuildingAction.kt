package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.action.upgrade

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode
import com.orego.battlecrane.bc.android.api.model.action.BUiAction
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BUiHumanAssets
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.util.BLevelable
import com.orego.battlecrane.bc.engine.api.model.util.BProducable
import com.orego.battlecrane.bc.engine.standardImpl.race.human.event.BHumanUpgradeBuildingEvent
import com.orego.battlecrane.bc.engine.standardImpl.race.human.util.BHumanCalculations

class BUiSkirmishUpgradeBuildingAction(uiGameContext: BUiGameContext, private val producable: BProducable) :
    BUiAction(uiGameContext) {

    companion object {

        const val PATH = BUiHumanAssets.Action.Upgrate.PATH
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
        val availableUpgradeCount = BHumanCalculations.countPossibleBuildingUpgrades(context, playerId)
        return this.producable.isProduceEnable && availableUpgradeCount > 0
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

    inner class UiClickMode(uiGameContext: BUiGameContext) : BUiAction.UiClickMode(uiGameContext, this) {

        private val producable = this@BUiSkirmishUpgradeBuildingAction.producable

        private val gameContext: BGameContext = uiGameContext.gameContext

        override fun onNextClickMode(nextUiClickMode: BUiClickMode?): BUiClickMode? {
            if (nextUiClickMode is BUiUnit.UiClickMode) {
                val clickedUnit = nextUiClickMode.uiUnit.unit
                if (clickedUnit is BLevelable) {
                    val producableId = this.producable.producableId
                    val targetLevelableId = clickedUnit.levelableId
                    val event = BHumanUpgradeBuildingEvent(producableId, targetLevelableId)
                    val isSuccessful = event.isEnable(this.gameContext)
                    if (isSuccessful) {
                        this.gameContext.pipeline.broacastEvent(event)
                        this.action.dismiss(this.uiGameContext)
                        return null
                    }
                }
            }
            return this
        }
    }
}
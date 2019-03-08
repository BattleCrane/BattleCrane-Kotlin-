package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.action.attack

import com.orego.battlecrane.bc.android.api.asset.BUiAssets
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode
import com.orego.battlecrane.bc.android.api.model.action.BUiAction
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BUiHumanAssets
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.api.model.util.BAttackable
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.vehicle.trigger.BSkirmishHumanTankOnAttackActionTrigger


//TODO: MAKE ATTACK ACTION WITH ATTACK EVENT BUILDER PARAM!
class BUiSkirmishHumanTankAttackAction(uiGameContext: BUiGameContext, private val uiUnit : BUiUnit) :
    BUiAction(uiGameContext) {

    companion object {

        const val PATH = BUiHumanAssets.Action.Attack.PATH
    }

    private val attackable = this.uiUnit.unit as BAttackable

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

    override fun canActivate(uiGameContext: BUiGameContext) = this.attackable.isAttackEnable

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
        this.uiUnit.checkCommands(uiGameContext)
        if (this.uiUnit.canActivate(uiGameContext)) {
            this.uiUnit.viewMode = BUiAssets.ViewMode.ACTIVE
            this.uiUnit.updateView(uiGameContext)
            this.uiUnit.onHideInfo(uiGameContext)
            this.uiUnit.hideCommands(uiGameContext)
        } else {
            this.uiUnit.dismiss(uiGameContext)
        }
    }

    inner class UiClickMode(uiGameContext: BUiGameContext) : BUiAction.UiClickMode(uiGameContext, this) {

        private val attackable = this@BUiSkirmishHumanTankAttackAction.attackable

        private val gameContext: BGameContext = uiGameContext.gameContext

        override fun onNextClickMode(nextUiClickMode: BUiClickMode?): BUiClickMode? {
            println("MARINNEENEENNENENEN")
            if (nextUiClickMode is BUiUnit.UiClickMode && this.attackable is BUnit) {
                val clickedUnit = nextUiClickMode.uiUnit.unit
                val event = BSkirmishHumanTankOnAttackActionTrigger.Event(
                    this.attackable.attackableId,
                    this.attackable.x,
                    this.attackable.y,
                    clickedUnit.x,
                    clickedUnit.y
                )
                println("PAW1")
                val isSuccessful = event.isEnable(this.gameContext)
                println("SUCESSFUL PAW: $isSuccessful")
                if (isSuccessful) {
                    println("PAW2")
                    this.gameContext.pipeline.broacastEvent(event)
                    this.action.onPerform(this.uiGameContext)
                    return null
                }
            }
            return super.onNextClickMode(nextUiClickMode)
        }
    }
}
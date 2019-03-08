package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.action.attack

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.android.api.asset.BUiAssets
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode
import com.orego.battlecrane.bc.android.api.model.action.BUiAction
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.util.context.AndroidContext
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BUiHumanAssets
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.api.model.util.BAttackable
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.infantry.trigger.BSkirmishHumanMarineOnAttackActionTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.infantry.implementation.BHumanMarine

class BUiSkirmishHumanMarineAttackAction(uiGameContext: BUiGameContext, private val uiUnit : BUiUnit) :
    BUiAction(uiGameContext) {

    companion object {

        const val PATH = BUiHumanAssets.Action.Attack.PATH

        const val ACTION_NAME = "Attack enemy"
    }

    var informer: Informer? = null

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
     * Info.
     */

    override fun onShowInfo(uiGameContext: BUiGameContext) {
        this.informer?.showInfo(uiGameContext)
    }

    override fun onHideInfo(uiGameContext: BUiGameContext) {
        this.informer?.hideInfo(uiGameContext)
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

        private val attackable = this@BUiSkirmishHumanMarineAttackAction.attackable

        private val gameContext: BGameContext = uiGameContext.gameContext

        override fun onNextClickMode(nextUiClickMode: BUiClickMode?): BUiClickMode? {
            if (nextUiClickMode is BUiUnit.UiClickMode && this.attackable is BUnit) {
                val clickedUnit = nextUiClickMode.uiUnit.unit
                val event = BSkirmishHumanMarineOnAttackActionTrigger.Event(
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

    /**
     * Represents a information about unit.
     */

    abstract class Informer(protected val marine: BHumanMarine) {

        companion object {

            const val DESCRIPTION = "Can attack through your any units and enemy creatures"
        }

        protected open val characteristicsText: String
            get() {
                val marine = this.marine
                val damage = marine.damage
                return "Deal $damage damage to an enemy"
            }

        protected open val descriptionText: String = DESCRIPTION

        /**
         * Show.
         */

        fun showInfo(uiGameContext: BUiGameContext) {
            uiGameContext.uiProvider.apply {
                this.itemNameTextView.showItemName()
                this.itemCharacteristicsConstraintLayout.showCharacteristics(this.androidContext)
                this.itemDescriptionConstraintLayout.showDescription(this.androidContext)
            }
        }

        private fun TextView.showItemName() {
            this.text = ACTION_NAME
        }

        private fun ConstraintLayout.showCharacteristics(androidContext: AndroidContext) {
            val layoutId = this.id
            val size = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
            val layoutParams = ConstraintLayout.LayoutParams(size, size)
                .also {
                    it.startToStart = layoutId
                    it.endToEnd = layoutId
                    it.topToTop = layoutId
                    it.bottomToBottom = layoutId
                    it.marginStart = 0
                    it.marginEnd = 0
                    it.topMargin = 0
                    it.bottomMargin = 0
                    it.horizontalBias = 0.5f
                    it.verticalBias = 0.5f
                }
            val characteristicsTextView = TextView(androidContext)
                .also {
                    it.id = View.generateViewId()
                    it.text = this@Informer.characteristicsText
                    it.textSize = 16f
                    it.setTextColor(androidContext.getColor(R.color.bc_text))
                    it.gravity = Gravity.CENTER
                    it.layoutParams = layoutParams
                }
            this.addView(characteristicsTextView)
        }

        private fun ConstraintLayout.showDescription(androidContext: AndroidContext) {
            val layoutId = this.id
            val size = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
            val layoutParams = ConstraintLayout.LayoutParams(size, size)
                .also {
                    it.startToStart = layoutId
                    it.endToEnd = layoutId
                    it.topToTop = layoutId
                    it.bottomToBottom = layoutId
                    it.marginStart = 0
                    it.marginEnd = 0
                    it.topMargin = 0
                    it.bottomMargin = 0
                    it.horizontalBias = 0.5f
                    it.verticalBias = 0.5f
                }
            val characteristicsTextView = TextView(androidContext)
                .also {
                    it.id = View.generateViewId()
                    it.text = this@Informer.descriptionText
                    it.textSize = 16f
                    it.setTextColor(androidContext.getColor(R.color.bc_text))
                    it.gravity = Gravity.CENTER
                    it.layoutParams = layoutParams
                }
            this.addView(characteristicsTextView)
        }

        /**
         * Hide.
         */

        fun hideInfo(uiGameContext: BUiGameContext) {
            uiGameContext.uiProvider.apply {
                this.itemNameTextView.hideItemName()
                this.itemCharacteristicsConstraintLayout.hideCharacteristics()
                this.itemDescriptionConstraintLayout.hideDescription()
            }
        }

        private fun TextView.hideItemName() {
            this.text = ""
        }

        private fun ConstraintLayout.hideCharacteristics() {
            this.removeAllViews()
        }

        private fun ConstraintLayout.hideDescription() {
            this.removeAllViews()
        }
    }
}
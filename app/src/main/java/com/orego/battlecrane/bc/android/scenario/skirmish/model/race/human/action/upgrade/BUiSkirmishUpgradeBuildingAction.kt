package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.action.upgrade

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
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.action.train.BUiSkirmishTrainHumanMarineAction
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BUiHumanAssets
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.util.BLevelable
import com.orego.battlecrane.bc.engine.api.model.util.BProducable
import com.orego.battlecrane.bc.engine.standardImpl.race.human.event.BHumanUpgradeBuildingEvent
import com.orego.battlecrane.bc.engine.standardImpl.race.human.util.BHumanCalculations

class BUiSkirmishUpgradeBuildingAction(uiGameContext: BUiGameContext, private val uiUnit: BUiUnit) :
    BUiAction(uiGameContext) {

    companion object {

        const val PATH = BUiHumanAssets.Action.Upgrate.PATH

        const val ACTION_NAME = "Upgrade a building"
    }

    private val producable = this.uiUnit.unit as BProducable

    var informer: Informer? = Informer()

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
                        this.action.onPerform(this.uiGameContext)
                        return null
                    }
                }
            }
            return super.onNextClickMode(nextUiClickMode)
        }
    }

    /**
     * Represents a information about unit.
     */

    open class Informer {

        companion object {

            const val CHARACTERISTICS = "Increases the level of your building by 1"

            const val DESCRIPTION = ""
        }

        protected open val characteristicsText = CHARACTERISTICS

        protected open val descriptionText = DESCRIPTION

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
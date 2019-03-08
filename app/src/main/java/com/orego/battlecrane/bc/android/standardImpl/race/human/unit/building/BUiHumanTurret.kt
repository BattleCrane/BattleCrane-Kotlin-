package com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.util.context.AndroidContext
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BUiHumanAssets
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanTurret

class BUiHumanTurret private constructor(uiGameContext: BUiGameContext, override val unit: BHumanTurret) :
    BUiUnit(uiGameContext, unit) {

    companion object {

        const val PATH = "${BUiHumanAssets.Unit.Building.PATH}/turret"

        const val UNIT_NAME = "Turret"
    }

    var informer: Informer? = null

    override fun getAssetPath(): String {
        val viewKey = this.viewMode.key
        val level = this.unit.currentLevel
        val hitPoints = this.unit.currentHitPoints
        return "$PATH/$viewKey/${level}_$hitPoints.png"
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
     * Builder.
     */

    open class Builder(override val unit: BHumanTurret) : BUiUnit.Builder(unit) {

        override fun onCreate(uiGameContext: BUiGameContext): BUiHumanTurret {
            return BUiHumanTurret(uiGameContext, this.unit)
        }
    }

    /**
     * Represents a information about unit.
     */

    abstract class Informer(protected val turret: BHumanTurret) {

        protected open val characteristicsText: String
            get() {
                val turret = this.turret
                val currentHitPoints = turret.currentHitPoints
                val maxHitPoints = turret.maxHitPoints
                val currentLevel = turret.currentLevel
                val maxLevel = turret.maxLevel
                val damage = turret.damage
                val radiusAttack = turret.radiusAttack
                return "Building\n" +
                        "Size: 1x1\n" +
                        "Hit points: $currentHitPoints/$maxHitPoints\n" +
                        "Damage: $damage\n" +
                        "Radius attack: $radiusAttack" +
                        "Level: $currentLevel/$maxLevel"
            }

        protected abstract val descriptionText: String

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
            this.text = UNIT_NAME
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
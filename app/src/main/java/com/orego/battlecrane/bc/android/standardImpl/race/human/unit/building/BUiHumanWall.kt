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
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanWall

class BUiHumanWall private constructor(uiGameContext: BUiGameContext, override val unit: BHumanWall) :
    BUiUnit(uiGameContext, unit) {

    companion object {

        const val PATH = "${BUiHumanAssets.Unit.Building.PATH}/wall"

        const val UNIT_NAME = "Wall"
    }

    var informer: Informer? = null

    override fun getAssetPath(): String {
        val viewKey = this.viewMode.key
        val hitPoints = this.unit.currentHitPoints
        return "$PATH/$viewKey/$hitPoints.png"
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

    open class Builder(override val unit: BHumanWall) : BUiUnit.Builder(unit) {

        override fun onCreate(uiGameContext: BUiGameContext) =
            BUiHumanWall(uiGameContext, this.unit)
    }

    /**
     * Represents a information about unit.
     */

    abstract class Informer(protected val wall: BHumanWall) {

        protected open val characteristicsText: String
            get() {
                val wall = this.wall
                val currentHitPoints = wall.currentHitPoints
                val maxHitPoints = wall.maxHitPoints
                return "Building\n" +
                        "Size: 1x1\n" +
                        "Hit points: $currentHitPoints/$maxHitPoints"
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
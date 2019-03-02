package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.barracks.trigger

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.util.BUiToolBuilder
import com.orego.battlecrane.bc.android.api.util.trigger.producable.BUiOnProduceEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BUiHumanAssets
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanBarracks
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.unit.type.BEmptyField
import com.orego.battlecrane.bc.engine.api.util.trigger.producable.BOnProduceEnableTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.barracks.trigger.BSkirmishHumanBarracksOnProduceActionTrigger

class BUiSkirmishHumanBarracksOnProduceEnableTrigger private constructor(
    uiGameContext: BUiGameContext,
    override val uiUnit: BUiHumanBarracks
) : BUiOnProduceEnableTrigger(uiGameContext, uiUnit) {

    private val trainMarineUiClickMode = TrainMarineUiClickMode()

    private val actionImageViewSet = mutableSetOf<ImageView>()

    override fun onDrawActions() {
        //Get right layout:
        val constraintLayout = this.uiGameContext.uiProvider.commandConstraintLayout
        val constraintLayoutId = constraintLayout.id
        val cellSize = constraintLayout.measuredWidth / COLUMN_COUNT
        //Get barracks:
        val barracks = this.uiUnit.item
        constraintLayout.removeAllViews()
        this.actionImageViewSet.clear()
        if (barracks.isProduceEnable) {
            //Create images:
            this.actionImageViewSet.add(
                BUiToolBuilder.build(this.uiGameContext, BUiHumanAssets.Train.MARINE, this.trainMarineUiClickMode)
            )
            var x = 0
            var y = 0
            for (imageView in this.actionImageViewSet) {
                if (x == COLUMN_COUNT) {
                    x = 0
                    y++
                }
                val constraintParams = ConstraintLayout.LayoutParams(cellSize, cellSize)
                constraintParams.startToStart = constraintLayoutId
                constraintParams.topToTop = constraintLayoutId
                constraintParams.marginStart = cellSize * x
                constraintParams.topMargin = cellSize * y
                imageView.layoutParams = constraintParams
                constraintLayout.addView(imageView)
                x++
            }
        }
    }

    /**
     * Click mode.
     */

    private inner class TrainMarineUiClickMode : BUiClickMode {

        private val unit = this@BUiSkirmishHumanBarracksOnProduceEnableTrigger.uiUnit.item

        private val gameContext: BGameContext = this@BUiSkirmishHumanBarracksOnProduceEnableTrigger.context

        override fun onNextClickMode(nextUiClickMode: BUiClickMode?): BUiClickMode? {
            if (nextUiClickMode is BUiUnit.UiClickMode) {
                val clickedUnit = nextUiClickMode.unit.item
                if (clickedUnit is BEmptyField) {
                    val event = BSkirmishHumanBarracksOnProduceActionTrigger.Event(
                        this.unit.producableId,
                        clickedUnit.x,
                        clickedUnit.y
                    )
                    val isSuccessful = event.isEnable(this.gameContext, this.unit)
                    if (isSuccessful) {
                        this.gameContext.pipeline.broacastEvent(event)
                        this@BUiSkirmishHumanBarracksOnProduceEnableTrigger.onDrawActions()
                        return null
                    }
                }
            }
            return this
        }
    }

    companion object {

//        @MagicConstant
//        private const val CELL_COEFFICIENT = 0.9

        private const val COLUMN_COUNT = 2

        fun connect(uiGameContext: BUiGameContext, holder: BUiHumanBarracks) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnProduceEnableTrigger && node.producable == holder.item
            }
            val uiTrigger = BUiSkirmishHumanBarracksOnProduceEnableTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}
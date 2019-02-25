package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.infantry.trigger

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bc.android.api.asset.BUiCommonPaths
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.util.BUiToolBuilder
import com.orego.battlecrane.bc.android.api.util.trigger.attackable.BUiOnAttackEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.infantry.BUiHumanMarine
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.util.trigger.attack.BOnAttackEnableTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.infantry.trigger.BSkirmishHumanMarineOnAttackActionTrigger

class BUiSkirmishHumanMarineHolderOnAttackEnableTrigger private constructor(
    uiGameContext: BUiGameContext,
    override val uiUnit: BUiHumanMarine
) : BUiOnAttackEnableTrigger(uiGameContext, uiUnit) {

    private val actionImageViewSet = mutableSetOf<ImageView>()

    override fun onDrawActions() {
        //Get right layout:
        val constraintLayout = this.uiGameContext.uiProvider.commandConstraintLayout
        val constraintLayoutId = constraintLayout.id
        val cellSize = constraintLayout.measuredWidth / COLUMN_COUNT
        //Get marine:
        val marine = this.uiUnit.item
        constraintLayout.removeAllViews()
        this.actionImageViewSet.clear()
        if (marine.isAttackEnable) {
            //Create images:
            this.actionImageViewSet.add(
                BUiToolBuilder.build(this.uiGameContext, BUiCommonPaths.Action.ATTACK, AttackUiClickMode())
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

    private inner class AttackUiClickMode : BUiClickMode {

        private val unit = this@BUiSkirmishHumanMarineHolderOnAttackEnableTrigger.uiUnit.item

        private val gameContext: BGameContext = this@BUiSkirmishHumanMarineHolderOnAttackEnableTrigger.context

        override fun onNextClickMode(nextUiClickMode: BUiClickMode?): BUiClickMode? {
            println("MARINNEENEENNENENEN")
            if (nextUiClickMode is BUiUnit.UiClickMode) {
                val clickedUnit = nextUiClickMode.unit.item
                val event = BSkirmishHumanMarineOnAttackActionTrigger.Event(
                    this.unit.attackableId,
                    this.unit.x,
                    this.unit.y,
                    clickedUnit.x,
                    clickedUnit.y
                )
                println("PAW1")
                val isSuccessful = event.isEnable(this.gameContext)
                println("SUCESSFUL PAW: $isSuccessful")
                if (isSuccessful) {
                    println("PAW2")
                    this.gameContext.pipeline.broacastEvent(event)
                    this@BUiSkirmishHumanMarineHolderOnAttackEnableTrigger.onDrawActions()
                    return null
                }
            }
            return this
        }
    }

    companion object {

//        @MagicConstant
//        private const val CELL_COEFFICIENT = 0.9

        private const val COLUMN_COUNT = 2

        fun connect(uiGameContext: BUiGameContext, holder: BUiHumanMarine) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnAttackEnableTrigger && node.attackable == holder.item
            }
            val uiTrigger = BUiSkirmishHumanMarineHolderOnAttackEnableTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}
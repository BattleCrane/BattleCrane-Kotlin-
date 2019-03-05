package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.vehicle.trigger

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bc.android.api.asset.BUiAssets
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.util.BUiToolBuilder
import com.orego.battlecrane.bc.android.api.util.trigger.attackable.BUiOnAttackEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.vehicle.BUiHumanTank
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.util.trigger.attack.BOnAttackEnableTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.vehicle.trigger.BSkirmishHumanTankOnAttackActionTrigger

class BUiSkirmishHumanTankHolderOnAttackEnableTrigger private constructor(
    uiGameContext: BUiGameContext,
    override val uiUnit: BUiHumanTank
) : BUiOnAttackEnableTrigger(uiGameContext, uiUnit) {

    private val actionImageViewSet = mutableSetOf<ImageView>()

    override fun onDrawActions() {
        //Get right layout:
        val constraintLayout = this.uiGameContext.uiProvider.commandConstraintLayout
        val constraintLayoutId = constraintLayout.id
        val cellSize = constraintLayout.measuredWidth / COLUMN_COUNT
        //Get tank:
        val tank = this.uiUnit.unit
        constraintLayout.removeAllViews()
        this.actionImageViewSet.clear()
        if (tank.isAttackEnable) {
            //Create images:
            this.actionImageViewSet.add(
                //TODO: ADD ATTACK BUTTON!
                BUiToolBuilder.build(this.uiGameContext, BUiAssets.CommonPath.ATTACK, AttackUiClickMode())
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

        private val unit = this@BUiSkirmishHumanTankHolderOnAttackEnableTrigger.uiUnit.unit

        private val gameContext: BGameContext = this@BUiSkirmishHumanTankHolderOnAttackEnableTrigger.context

        override fun onNextClickMode(nextUiClickMode: BUiClickMode?): BUiClickMode? {
            if (nextUiClickMode is BUiUnit.UiClickMode) {
                val clickedUnit = nextUiClickMode.unit.item
                val event = BSkirmishHumanTankOnAttackActionTrigger.Event(
                    this.unit.attackableId,
                    this.unit.x,
                    this.unit.y,
                    clickedUnit.x,
                    clickedUnit.y
                )
                val isSuccessful = event.isEnable(this.gameContext)
                if (isSuccessful) {
                    this.gameContext.pipeline.broacastEvent(event)
                    this@BUiSkirmishHumanTankHolderOnAttackEnableTrigger.onDrawActions()
                    return null
                }
            }
            return this
        }
    }

    companion object {

//        private const val CELL_COEFFICIENT = 0.9
//        @MagicConstant

        private const val COLUMN_COUNT = 2

        fun connect(uiGameContext: BUiGameContext, holder: BUiHumanTank) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnAttackEnableTrigger && node.attackable == holder.unit
            }
            val uiTrigger = BUiSkirmishHumanTankHolderOnAttackEnableTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}
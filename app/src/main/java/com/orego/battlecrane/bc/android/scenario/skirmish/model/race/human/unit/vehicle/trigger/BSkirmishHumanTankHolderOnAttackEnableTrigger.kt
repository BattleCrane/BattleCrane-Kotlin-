package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.vehicle.trigger

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.android.api.asset.BUiCommonPaths
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode
import com.orego.battlecrane.bc.android.api.context.heap.BUiUnitHeap
import com.orego.battlecrane.bc.android.api.model.unit.BUnitHolder
import com.orego.battlecrane.bc.android.api.util.BToolBuilder
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.vehicle.BHumanTankHolder
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.BOnAttackEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.util.trigger.attack.BOnAttackEnableTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.vehicle.trigger.BSkirmishHumanTankOnAttackActionTrigger
import com.orego.battlecrane.ui.util.gone
import com.orego.battlecrane.ui.util.show
import org.intellij.lang.annotations.MagicConstant

class BSkirmishHumanTankHolderOnAttackEnableTrigger private constructor(
    private val uiGameContext: BUiGameContext,
    val holder: BHumanTankHolder
) : BNode(uiGameContext.gameContext) {

    private val unitMap = this.context.storage.getHeap(BUiUnitHeap::class.java).objectMap

    /**
     * ImageView.
     */

    private val isEnableImageView: ImageView = this.createIsEnableImageView()

    private val actionImageViewSet = mutableSetOf<ImageView>()

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnAttackEnablePipe.Event && event.attackableId == this.holder.item.attackableId) {
            val animation: suspend () -> Unit =
                if (event.isEnable) {
                    { this.isEnableImageView.show() }
                } else {
                    { this.isEnableImageView.gone() }
                }
            this.uiGameContext.uiTaskManager.addTask(animation)
        }
        return null
    }

    private fun createIsEnableImageView(): ImageView {
        val uiProvider = this.uiGameContext.uiProvider
        val applicationContext = uiProvider.applicationContext
        val constraintLayout = uiProvider.mapConstraintLayout
        //Create image unitView:
        val imageView = ImageView(applicationContext)
        imageView.id = View.generateViewId()
        imageView.background = applicationContext.getDrawable(R.color.colorReady)
        imageView.layoutParams = this.holder.unitView.layoutParams
        imageView.gone()
        imageView.setOnClickListener {
            this.uiGameContext.uiClickController.pushClickMode(AttackUiClickMode())

//            this.refreshActions()
//            this.holder.showDescription(this.uiGameContext)
        }
        constraintLayout.addView(imageView)
        return imageView
    }

    private fun refreshActions() {
        //Get right layout:
        val constraintLayout = this.uiGameContext.uiProvider.rightConstraintLayout
        val constraintLayoutId = constraintLayout.id
        val columnSize = constraintLayout.measuredWidth / COLUMN_COUNT
        val cellSize = (columnSize * CELL_COEFFICIENT).toInt()
        //Get tank:
        val tank = this.holder.item
        constraintLayout.removeAllViews()
        this.actionImageViewSet.clear()
        if (tank.isAttackEnable) {
            //Create images:
            this.actionImageViewSet.add(
                //TODO: ADD ATTACK BUTTON!
                BToolBuilder.build(this.uiGameContext, BUiCommonPaths.Action.ATTACK, AttackUiClickMode())
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
                constraintParams.marginStart = columnSize * x
                constraintParams.topMargin = columnSize * y
                imageView.layoutParams = constraintParams
                constraintLayout.addView(imageView)
                x++
            }
        }
    }

    override fun intoPipe() = Pipe()

    override fun isFinished() = !this.unitMap.containsKey(this.holder.uiUnitId)

    /**
     * Click mode.
     */

    private inner class UiClickMode : BUnitHolder.UiClickMode(this.holder) {

        override fun onStartClickMode() {
            this@BSkirmishHumanTankHolderOnAttackEnableTrigger.refreshActions()
            this.unitHolder.showDescription(this@BSkirmishHumanTankHolderOnAttackEnableTrigger.uiGameContext)
        }

        override fun onNextClickMode(nextUiClickMode: BUiClickMode) = nextUiClickMode.also { it.onStartClickMode() }
    }

    private inner class AttackUiClickMode : BUiClickMode {

        private val unit = this@BSkirmishHumanTankHolderOnAttackEnableTrigger.holder.item

        private val gameContext: BGameContext = this@BSkirmishHumanTankHolderOnAttackEnableTrigger.context

        override fun onNextClickMode(nextUiClickMode: BUiClickMode): BUiClickMode? {
            if (nextUiClickMode is BUnitHolder.UiClickMode) {
                val clickedUnit = nextUiClickMode.unitHolder.item
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
                    this@BSkirmishHumanTankHolderOnAttackEnableTrigger.refreshActions()
                    return null
                }
            }
            return this
        }
    }

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val holder = this@BSkirmishHumanTankHolderOnAttackEnableTrigger.holder

        override fun isFinished() = this@BSkirmishHumanTankHolderOnAttackEnableTrigger.isFinished()
    }

    companion object {

        @MagicConstant
        private const val CELL_COEFFICIENT = 0.9

        private const val COLUMN_COUNT = 2

        fun connect(uiGameContext: BUiGameContext, holder: BHumanTankHolder) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnAttackEnableTrigger && node.attackable == holder.item
            }
            val uiTrigger = BSkirmishHumanTankHolderOnAttackEnableTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}
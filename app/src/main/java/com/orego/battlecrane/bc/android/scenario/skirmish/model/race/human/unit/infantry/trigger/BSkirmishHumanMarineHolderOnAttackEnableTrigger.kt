package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.infantry.trigger

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BClickMode
import com.orego.battlecrane.bc.android.api.context.heap.BUnitHolderHeap
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.android.api.util.BToolBuilder
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BHumanPaths
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.infantry.BHumanMarineHolder
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.BOnAttackEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.model.unit.type.BEmptyField
import com.orego.battlecrane.bc.engine.api.util.trigger.attack.BOnAttackEnableTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.infantry.trigger.BSkirmishHumanMarineOnAttackActionTrigger
import com.orego.battlecrane.ui.util.gone
import com.orego.battlecrane.ui.util.show
import org.intellij.lang.annotations.MagicConstant

class BSkirmishHumanMarineHolderOnAttackEnableTrigger private constructor(
    private val uiGameContext: BUiGameContext,
    val holder: BHumanMarineHolder
) : BNode(uiGameContext.gameContext) {

    private val unitMap = this.context.storage.getHeap(BUnitHolderHeap::class.java).objectMap

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
            this.uiGameContext.uiPipe.addAnimation(animation)
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
            this.refreshActions()
            this.holder.showDescription(this.uiGameContext)
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
        //Get marine:
        val marine = this.holder.item
        constraintLayout.removeAllViews()
        this.actionImageViewSet.clear()
        if (marine.isAttackEnable) {
            //Create images:
            this.actionImageViewSet.add(
                BToolBuilder.build(this.uiGameContext, BHumanPaths.Train.MARINE, ClickMode())
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

    private inner class ClickMode : BClickMode {

        private val unit = this@BSkirmishHumanMarineHolderOnAttackEnableTrigger.holder.item

        private val gameContext: BGameContext = this@BSkirmishHumanMarineHolderOnAttackEnableTrigger.context

        override fun handle(nextClickMode: BClickMode): BClickMode? {
            if (nextClickMode is BUnitHolder.ClickMode) {
                val clickedUnit = nextClickMode.unitHolder.item
                if (clickedUnit is BEmptyField) {
                    val event = BSkirmishHumanMarineOnAttackActionTrigger.Event(
                        this.unit.attackableId,
                        this.unit.x,
                        this.unit.y,
                        clickedUnit.x,
                        clickedUnit.y
                    )
                    val isSuccessful = event.isEnable(this.gameContext)
                    if (isSuccessful) {
                        this.gameContext.pipeline.broacastEvent(event)
                        this@BSkirmishHumanMarineHolderOnAttackEnableTrigger.refreshActions()
                        return null
                    }
                }
            }
            return this
        }
    }

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val holder = this@BSkirmishHumanMarineHolderOnAttackEnableTrigger.holder

        override fun isUnused() = this@BSkirmishHumanMarineHolderOnAttackEnableTrigger.isFinished()
    }

    companion object {

        @MagicConstant
        private const val CELL_COEFFICIENT = 0.9

        private const val COLUMN_COUNT = 2

        fun connect(uiGameContext: BUiGameContext, holder: BHumanMarineHolder) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnAttackEnableTrigger && node.attackable == holder.item
            }
            val uiTrigger = BSkirmishHumanMarineHolderOnAttackEnableTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}
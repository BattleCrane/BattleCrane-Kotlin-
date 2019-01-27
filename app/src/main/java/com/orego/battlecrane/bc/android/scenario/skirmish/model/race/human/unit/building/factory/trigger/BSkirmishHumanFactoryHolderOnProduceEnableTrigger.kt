package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.factory.trigger

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode
import com.orego.battlecrane.bc.android.api.context.heap.BUiUnitHeap
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.util.BToolBuilder
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BHumanPaths
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanFactory
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.model.unit.type.BEmptyField
import com.orego.battlecrane.bc.engine.api.util.trigger.producable.BOnProduceEnableTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.factory.trigger.BSkirmishHumanFactoryOnProduceActionTrigger
import com.orego.battlecrane.ui.util.gone
import com.orego.battlecrane.ui.util.show
import org.intellij.lang.annotations.MagicConstant

class BSkirmishHumanFactoryHolderOnProduceEnableTrigger private constructor(
    private val uiGameContext: BUiGameContext,
    val holder: BUiHumanFactory
) : BNode(uiGameContext.gameContext) {

    private val unitMap = this.context.storage.getHeap(BUiUnitHeap::class.java).objectMap

    /**
     * ImageView.
     */

    private val isEnableImageView: ImageView = this.createIsEnableImageView()

    private val actionImageViewSet = mutableSetOf<ImageView>()

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnProduceEnablePipe.Event && event.producableId == this.holder.item.producableId) {
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
            this.uiGameContext.uiClickController.pushClickMode(UiClickMode())

//            this.refreshActions()
//            this.uiUnit.showDescription(this.uiGameContext)
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
        //Get factory:
        val factory = this.holder.item
        constraintLayout.removeAllViews()
        this.actionImageViewSet.clear()
        if (factory.isProduceEnable) {
            //Create images:
            this.actionImageViewSet.add(
                BToolBuilder.build(this.uiGameContext, BHumanPaths.Produce.TANK, ProduceTankUiClickMode())
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

    private inner class UiClickMode : BUiUnit.UiClickMode(this.holder) {

        override fun onStartClickMode() {
            this@BSkirmishHumanFactoryHolderOnProduceEnableTrigger.refreshActions()
            this.unit.showDescription(this@BSkirmishHumanFactoryHolderOnProduceEnableTrigger.uiGameContext)
        }

        override fun onNextClickMode(nextUiClickMode: BUiClickMode) = nextUiClickMode.also { it.onStartClickMode() }
    }

    private inner class ProduceTankUiClickMode : BUiClickMode {

        private val unit = this@BSkirmishHumanFactoryHolderOnProduceEnableTrigger.holder.item

        private val gameContext: BGameContext = this@BSkirmishHumanFactoryHolderOnProduceEnableTrigger.context

        override fun onNextClickMode(nextUiClickMode: BUiClickMode): BUiClickMode? {
            if (nextUiClickMode is BUiUnit.UiClickMode) {
                val clickedUnit = nextUiClickMode.unit.item
                if (clickedUnit is BEmptyField) {
                    val event = BSkirmishHumanFactoryOnProduceActionTrigger.Event(
                        this.unit.producableId,
                        clickedUnit.x,
                        clickedUnit.y
                    )
                    val isSuccessful = event.isEnable(this.gameContext, this.unit)
                    if (isSuccessful) {
                        println("CAN PRODUCE TANK!")
                        this.gameContext.pipeline.broacastEvent(event)
                        this@BSkirmishHumanFactoryHolderOnProduceEnableTrigger.refreshActions()
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

        val holder = this@BSkirmishHumanFactoryHolderOnProduceEnableTrigger.holder

        override fun isFinished() = this@BSkirmishHumanFactoryHolderOnProduceEnableTrigger.isFinished()
    }

    companion object {

        @MagicConstant
        private const val CELL_COEFFICIENT = 0.9

        private const val COLUMN_COUNT = 2

        fun connect(uiGameContext: BUiGameContext, holder: BUiHumanFactory) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnProduceEnableTrigger && node.producable == holder.item
            }
            val uiTrigger = BSkirmishHumanFactoryHolderOnProduceEnableTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}
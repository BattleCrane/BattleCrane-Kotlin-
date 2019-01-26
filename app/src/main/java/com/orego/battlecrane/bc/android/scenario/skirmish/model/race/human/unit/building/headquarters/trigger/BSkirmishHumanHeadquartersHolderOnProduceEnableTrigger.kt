package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.headquarters.trigger

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode
import com.orego.battlecrane.bc.android.api.context.heap.BUiUnitHeap
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.android.api.util.BToolBuilder
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BHumanPaths
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BHumanHeadquartersHolder
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.model.property.BLevelable
import com.orego.battlecrane.bc.engine.api.model.unit.type.BEmptyField
import com.orego.battlecrane.bc.engine.api.util.trigger.producable.BOnProduceEnableTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.event.construct.*
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.utils.BSkirmishHumanRule
import com.orego.battlecrane.bc.engine.standardImpl.race.human.event.BHumanConstructBuildingEvent
import com.orego.battlecrane.bc.engine.standardImpl.race.human.event.BHumanUpgradeBuildingEvent
import com.orego.battlecrane.bc.engine.standardImpl.race.human.util.BHumanCalculations
import com.orego.battlecrane.ui.util.gone
import com.orego.battlecrane.ui.util.show
import org.intellij.lang.annotations.MagicConstant

class BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger private constructor(
    private val uiGameContext: BUiGameContext,
    val holder: BHumanHeadquartersHolder
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
        //Get headquarters:
        val headquarters = this.holder.item
        val producableId = headquarters.producableId
        val playerId = headquarters.playerId
        constraintLayout.removeAllViews()
        this.actionImageViewSet.clear()
        if (headquarters.isProduceEnable) {
            //Create images:
            this.actionImageViewSet.add(
                BToolBuilder.build(this.uiGameContext, BHumanPaths.Build.BARRACKS, object : BuildUiClickMode() {

                    override fun createEvent(x: Int, y: Int) =
                        BSkirmishHumanConstructBarracksEvent(producableId, x, y)
                })
            )
            this.actionImageViewSet.add(
                BToolBuilder.build(this.uiGameContext, BHumanPaths.Build.TURRET, object : BuildUiClickMode() {

                    override fun createEvent(x: Int, y: Int) =
                        BSkirmishHumanConstructTurretEvent(producableId, x, y)
                })
            )
            this.actionImageViewSet.add(
                BToolBuilder.build(this.uiGameContext, BHumanPaths.Build.WALL, object : BuildUiClickMode() {

                    override fun createEvent(x: Int, y: Int) =
                        BSkirmishHumanConstructWallEvent(producableId, x, y)
                })
            )
            if (BHumanCalculations.countGenerators(this.context, playerId) < BSkirmishHumanRule.GENERATOR_LIMIT) {
                this.actionImageViewSet.add(
                    BToolBuilder.build(this.uiGameContext, BHumanPaths.Build.GENERATOR, object : BuildUiClickMode() {

                        override fun createEvent(x: Int, y: Int) =
                            BSkirmishHumanConstructGeneratorEvent(producableId, x, y)
                    })
                )
            }
            if (BHumanCalculations.countDiffBarracksFactory(this.context, playerId) > 0) {
                this.actionImageViewSet.add(
                    BToolBuilder.build(this.uiGameContext, BHumanPaths.Build.FACTORY, object : BuildUiClickMode() {

                        override fun createEvent(x: Int, y: Int) =
                            BSkirmishHumanConstructFactoryEvent(producableId, x, y)
                    })
                )
            }
            if (BHumanCalculations.countPossibleBuildingUpgrades(this.context, playerId) > 0) {
                this.actionImageViewSet.add(
                    BToolBuilder.build(this.uiGameContext, BHumanPaths.Upgrade.BUILDING, UpgradeBuildingUiClickMode())
                )
            }
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
            this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.refreshActions()
            this.unitHolder.showDescription(this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.uiGameContext)
        }

        override fun onNextClickMode(nextUiClickMode: BUiClickMode) = nextUiClickMode.also { it.onStartClickMode() }
    }

    private abstract inner class BuildUiClickMode : BUiClickMode {

        private val unit = this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.holder.item

        private val gameContext: BGameContext = this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.context

        protected abstract fun createEvent(x: Int, y: Int): BHumanConstructBuildingEvent

        override fun onNextClickMode(nextUiClickMode: BUiClickMode): BUiClickMode? {
            if (nextUiClickMode is BUnitHolder.UiClickMode) {
                val clickedUnit = nextUiClickMode.unitHolder.item
                if (clickedUnit is BEmptyField) {
                    val event = this.createEvent(clickedUnit.x, clickedUnit.y)
                    val isSuccessful = event.isEnable(this.gameContext, this.unit.playerId)
                    if (isSuccessful) {
                        this.gameContext.pipeline.broacastEvent(event)
                        this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.refreshActions()
                        return null
                    }
                }
            }
            return this
        }
    }

    private inner class UpgradeBuildingUiClickMode : BUiClickMode {

        private val unit = this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.holder.item

        private val gameContext: BGameContext = this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.context

        private val uiPipe =
            this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.uiGameContext.uiTaskManager

        override fun onNextClickMode(nextUiClickMode: BUiClickMode): BUiClickMode? {
            if (nextUiClickMode is BUnitHolder.UiClickMode) {
                val clickedUnit = nextUiClickMode.unitHolder.item
                if (clickedUnit is BLevelable) {
                    val event = BHumanUpgradeBuildingEvent(this.unit.producableId, clickedUnit.levelableId)
                    val isSuccessful = event.isEnable(this.gameContext)
                    if (isSuccessful) {
                        this.gameContext.pipeline.broacastEvent(event)
                        this.uiPipe.addTask {
                            this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.refreshActions()
                        }
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

        val holder = this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.holder

        override fun isFinished() = this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.isFinished()
    }

    companion object {

        @MagicConstant
        private const val CELL_COEFFICIENT = 0.9

        private const val COLUMN_COUNT = 2

        fun connect(uiGameContext: BUiGameContext, holder: BHumanHeadquartersHolder) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnProduceEnableTrigger && node.producable == holder.item
            }
            val uiTrigger = BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}
package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.util.trigger

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.util.BUiToolBuilder
import com.orego.battlecrane.bc.android.api.util.trigger.producable.BUiOnProduceEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BUiHumanPaths
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.property.BLevelable
import com.orego.battlecrane.bc.engine.api.model.property.BProducable
import com.orego.battlecrane.bc.engine.api.model.unit.type.BEmptyField
import com.orego.battlecrane.bc.engine.api.util.trigger.producable.BOnProduceEnableTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.event.construct.*
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.utils.BSkirmishHumanRule
import com.orego.battlecrane.bc.engine.standardImpl.race.human.event.BHumanConstructBuildingEvent
import com.orego.battlecrane.bc.engine.standardImpl.race.human.event.BHumanUpgradeBuildingEvent
import com.orego.battlecrane.bc.engine.standardImpl.race.human.util.BHumanCalculations
import org.intellij.lang.annotations.MagicConstant

class BUiSkirmishHumanOnProduceBuildingEnableTrigger private constructor(
    uiGameContext: BUiGameContext,
    override val uiUnit: BUiUnit
) : BUiOnProduceEnableTrigger(uiGameContext, uiUnit) {

    private val actionImageViewSet = mutableSetOf<ImageView>()

    override fun onDrawActions() {
        //Get right layout:
        val constraintLayout = this.uiGameContext.uiProvider.rightConstraintLayout
        val constraintLayoutId = constraintLayout.id
        val columnSize = constraintLayout.measuredWidth / COLUMN_COUNT
        val cellSize = (columnSize * CELL_COEFFICIENT).toInt()
        //Get headquarters:
        val unitBuilder = this.uiUnit.item as BProducable
        val producableId = unitBuilder.producableId
        val playerId = unitBuilder.playerId
        constraintLayout.removeAllViews()
        this.actionImageViewSet.clear()
        if (unitBuilder.isProduceEnable) {
            //Create images:
            this.actionImageViewSet.add(
                BUiToolBuilder.build(this.uiGameContext, BUiHumanPaths.Build.BARRACKS, object : BuildUiClickMode() {

                    override fun createEvent(x: Int, y: Int) =
                        BSkirmishHumanConstructBarracksEvent(producableId, x, y)
                })
            )
            this.actionImageViewSet.add(
                BUiToolBuilder.build(this.uiGameContext, BUiHumanPaths.Build.TURRET, object : BuildUiClickMode() {

                    override fun createEvent(x: Int, y: Int) =
                        BSkirmishHumanConstructTurretEvent(producableId, x, y)
                })
            )
            this.actionImageViewSet.add(
                BUiToolBuilder.build(this.uiGameContext, BUiHumanPaths.Build.WALL, object : BuildUiClickMode() {

                    override fun createEvent(x: Int, y: Int) =
                        BSkirmishHumanConstructWallEvent(producableId, x, y)
                })
            )
            if (BHumanCalculations.countGenerators(this.context, playerId) < BSkirmishHumanRule.GENERATOR_LIMIT) {
                this.actionImageViewSet.add(
                    BUiToolBuilder.build(this.uiGameContext, BUiHumanPaths.Build.GENERATOR, object : BuildUiClickMode() {

                        override fun createEvent(x: Int, y: Int) =
                            BSkirmishHumanConstructGeneratorEvent(producableId, x, y)
                    })
                )
            }
            if (BHumanCalculations.countDiffBarracksFactory(this.context, playerId) > 0) {
                this.actionImageViewSet.add(
                    BUiToolBuilder.build(this.uiGameContext, BUiHumanPaths.Build.FACTORY, object : BuildUiClickMode() {

                        override fun createEvent(x: Int, y: Int) =
                            BSkirmishHumanConstructFactoryEvent(producableId, x, y)
                    })
                )
            }
            if (BHumanCalculations.countPossibleBuildingUpgrades(this.context, playerId) > 0) {
                this.actionImageViewSet.add(
                    BUiToolBuilder.build(this.uiGameContext, BUiHumanPaths.Upgrade.BUILDING, UpgradeBuildingUiClickMode())
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

    /**
     * Click mode.
     */

    private abstract inner class BuildUiClickMode : BUiClickMode {

        private val unit = this@BUiSkirmishHumanOnProduceBuildingEnableTrigger.uiUnit.item

        private val gameContext: BGameContext = this@BUiSkirmishHumanOnProduceBuildingEnableTrigger.context

        protected abstract fun createEvent(x: Int, y: Int): BHumanConstructBuildingEvent

        override fun onNextClickMode(nextUiClickMode: BUiClickMode?): BUiClickMode? {
            if (nextUiClickMode is BUiUnit.UiClickMode) {
                val clickedUnit = nextUiClickMode.unit.item
                if (clickedUnit is BEmptyField) {
                    val event = this.createEvent(clickedUnit.x, clickedUnit.y)
                    val isSuccessful = event.isEnable(this.gameContext, this.unit.playerId)
                    if (isSuccessful) {
                        this.gameContext.pipeline.broacastEvent(event)
                        this@BUiSkirmishHumanOnProduceBuildingEnableTrigger.onDrawActions()
                        return null
                    }
                }
            }
            return this
        }
    }

    private inner class UpgradeBuildingUiClickMode : BUiClickMode {

        private val unit = this@BUiSkirmishHumanOnProduceBuildingEnableTrigger.uiUnit.item

        private val gameContext: BGameContext = this@BUiSkirmishHumanOnProduceBuildingEnableTrigger.context

        private val uiPipe =
            this@BUiSkirmishHumanOnProduceBuildingEnableTrigger.uiGameContext.uiTaskManager

        override fun onNextClickMode(nextUiClickMode: BUiClickMode?): BUiClickMode? {
            if (nextUiClickMode is BUiUnit.UiClickMode) {
                val clickedUnit = nextUiClickMode.unit.item
                if (clickedUnit is BLevelable) {
                    val event = BHumanUpgradeBuildingEvent((this.unit as BProducable).producableId, clickedUnit.levelableId)
                    val isSuccessful = event.isEnable(this.gameContext)
                    if (isSuccessful) {
                        this.gameContext.pipeline.broacastEvent(event)
                        this.uiPipe.addTask {
                            this@BUiSkirmishHumanOnProduceBuildingEnableTrigger.onDrawActions()
                        }
                        return null
                    }
                }
            }
            return this
        }
    }

    companion object {

        @MagicConstant
        private const val CELL_COEFFICIENT = 0.9

        private const val COLUMN_COUNT = 2

        fun connect(uiGameContext: BUiGameContext, holder: BUiUnit) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnProduceEnableTrigger && node.producable == holder.item
            }
            val uiTrigger = BUiSkirmishHumanOnProduceBuildingEnableTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}
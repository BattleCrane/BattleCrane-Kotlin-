package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.headquarters.trigger

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.model.property.BLevelable
import com.orego.battlecrane.bc.engine.api.util.trigger.producable.BOnProduceEnableTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.event.BSkirmishHumanEvents
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation.BEmptyGrassField
import com.orego.battlecrane.bc.engine.standardImpl.race.human.util.BHumanEvents
import com.orego.battlecrane.bc.engine.standardImpl.race.human.util.BHumanCalculations
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BClickMode
import com.orego.battlecrane.bc.android.api.context.heap.BUnitHolderHeap
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.android.api.util.BToolBuilder
import com.orego.battlecrane.bc.android.standardImpl.race.human.asset.BHumanPaths
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BHumanHeadquartersHolder
import com.orego.battlecrane.ui.util.gone
import com.orego.battlecrane.ui.util.show
import org.intellij.lang.annotations.MagicConstant

class BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger private constructor(
    private val uiGameContext: BUiGameContext,
    val holder: BHumanHeadquartersHolder
) : BNode(uiGameContext.gameContext) {

    private val unitMap = this.context.storage.getHeap(BUnitHolderHeap::class.java).objectMap

    /**
     * ImageView.
     */

    private val isEnableImageView: ImageView = this.createIsEnableImageView()

    private val actionImageViewSet = mutableSetOf<ImageView>()

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnProduceEnablePipe.Event && event.producableId == this.holder.item.producableId) {
            println("BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger HANDLE!")
            println(this.holder.item)
            val animation: suspend () -> Unit =
                if (event.isEnable) {
                    { this.isEnableImageView.show() }
                } else {
                    { this.isEnableImageView.gone() }
                }
            this.uiGameContext.animationPipe.addAnimation(animation)
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
        //Get headquarters:
        val headquarters = this.holder.item
        val producableId = headquarters.producableId
        val playerId = headquarters.playerId
        constraintLayout.removeAllViews()
        this.actionImageViewSet.clear()
        if (headquarters.isProduceEnable) {
            //Create images:
            this.actionImageViewSet.add(
                BToolBuilder.build(this.uiGameContext, BHumanPaths.Build.BARRACKS, object : BuildClickMode() {

                    override fun createEvent(x: Int, y: Int) =
                        BSkirmishHumanEvents.Construct.BHumanConstructBarracksEvent(producableId, x, y)
                })
            )
            this.actionImageViewSet.add(
                BToolBuilder.build(this.uiGameContext, BHumanPaths.Build.TURRET, object : BuildClickMode() {

                    override fun createEvent(x: Int, y: Int) =
                        BSkirmishHumanEvents.Construct.TurretEvent(producableId, x, y)
                })
            )
            this.actionImageViewSet.add(
                BToolBuilder.build(this.uiGameContext, BHumanPaths.Build.WALL, object : BuildClickMode() {

                    override fun createEvent(x: Int, y: Int) =
                        BSkirmishHumanEvents.Construct.WallEvent(producableId, x, y)
                })
            )
            if (BHumanCalculations.countGenerators(this.context, playerId) < BHumanCalculations.GENERATOR_LIMIT) {
                this.actionImageViewSet.add(
                    BToolBuilder.build(this.uiGameContext, BHumanPaths.Build.GENERATOR, object : BuildClickMode() {

                        override fun createEvent(x: Int, y: Int) =
                            BSkirmishHumanEvents.Construct.GeneratorEvent(producableId, x, y)
                    })
                )
            }
            if (BHumanCalculations.countDiffBarracksFactory(this.context, playerId) > 0) {
                this.actionImageViewSet.add(
                    BToolBuilder.build(this.uiGameContext, BHumanPaths.Build.FACTORY, object : BuildClickMode() {

                        override fun createEvent(x: Int, y: Int) =
                            BSkirmishHumanEvents.Construct.FactoryEvent(producableId, x, y)
                    })
                )
            }
            if (BHumanCalculations.countPossibleBuildingUpgrades(this.context, playerId) > 0) {
                this.actionImageViewSet.add(
                    BToolBuilder.build(this.uiGameContext, BHumanPaths.Upgrade.BUILDING, UpgradeBuildingClickMode())
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

//    override fun isUnused() = this.unitMap.containsKey(this.holder.uiUnitId)

    /**
     * Click mode.
     */

    private abstract inner class BuildClickMode : BClickMode {

        private val unit = this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.holder.item

        private val gameContext: BGameContext = this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.context

        protected abstract fun createEvent(x: Int, y: Int): BHumanEvents.Construct.Event

        override fun handle(nextClickMode: BClickMode): BClickMode? {
            if (nextClickMode is BUnitHolder.ClickMode) {
                val clickedUnit = nextClickMode.unitHolder.item
                if (clickedUnit is BEmptyGrassField) {
                    val event = this.createEvent(clickedUnit.x, clickedUnit.y)
                    val isSuccessful = event.isEnable(this.gameContext, this.unit.playerId)
                    if (isSuccessful) {
                        println("BUILD COMPLETE!!!")
                        this.gameContext.pipeline.broacastEvent(event)
                        this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.refreshActions()
                        return null
                    }
                    println("PASS!!!")
                }
            }
            return this
        }
    }

    private inner class UpgradeBuildingClickMode : BClickMode {

        private val unit = this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.holder.item

        private val gameContext: BGameContext = this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.context

        private val animationPipe =
            this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.uiGameContext.animationPipe

        override fun handle(nextClickMode: BClickMode): BClickMode? {
            if (nextClickMode is BUnitHolder.ClickMode) {
                val clickedUnit = nextClickMode.unitHolder.item
                if (clickedUnit is BLevelable) {
                    val event = BHumanEvents.Upgrade.Event(this.unit.producableId, clickedUnit.levelableId)
                    val isSuccessful = event.isEnable(this.gameContext)
                    if (isSuccessful) {
                        println("UPGRADE COMPLETE!!!")
                        this.gameContext.pipeline.broacastEvent(event)
                        this.animationPipe.addAnimation {
                            this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.refreshActions()
                        }
                        return null
                    }
                    println("PASS!!!")
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

        override fun isUnused() = this@BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger.isUnused()
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
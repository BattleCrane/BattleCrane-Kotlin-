package com.orego.battlecrane.ui.model.scenario.skirmish.model.race.human.unit.building.headquarters.trigger

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.model.property.producable.trigger.BOnProduceEnableTrigger
import com.orego.battlecrane.bc.std.race.human.util.BHumanTools
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.context.heap.BUnitHolderHeap
import com.orego.battlecrane.ui.model.std.race.human.unit.building.BHumanHeadquartersHolder
import com.orego.battlecrane.ui.model.scenario.skirmish.model.race.human.action.building.*
import com.orego.battlecrane.ui.util.gone
import com.orego.battlecrane.ui.util.show
import org.intellij.lang.annotations.MagicConstant

class BSkirmishHumanHeadquartersHolderOnProduceEnableTrigger private constructor(
    uiGameContext: BUiGameContext,
    private val holder: BHumanHeadquartersHolder
) : BNode(uiGameContext.gameContext) {

    private val animationPipe = uiGameContext.animationPipe

    private val unitMap = this.context.storage.getHeap(BUnitHolderHeap::class.java).objectMap

    /**
     * ImageView.
     */

    private val isEnableImageView: ImageView = this.createBackground(uiGameContext)

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
            this.animationPipe.addAnimation(animation)
        }
        return null
    }

    private fun createBackground(uiGameContext: BUiGameContext): ImageView {
        val uiProvider = uiGameContext.uiProvider
        val applicationContext = uiProvider.applicationContext
        val constraintLayout = uiProvider.mapConstraintLayout
        //Create image unitView:
        val imageView = ImageView(applicationContext)
        imageView.id = View.generateViewId()
        imageView.background = applicationContext.getDrawable(R.color.colorReady)
        imageView.layoutParams = this.holder.unitView.layoutParams
        imageView.gone()
        imageView.setOnClickListener {
            this.showActions(uiGameContext)
            this.holder.showDescription(uiGameContext)
        }
        constraintLayout.addView(imageView)
        return imageView
    }

    private fun showActions(uiGameContext: BUiGameContext) {
        //Get right layout:
        val constraintLayout = uiGameContext.uiProvider.rightConstraintLayout
        val constraintLayoutId = constraintLayout.id
        val columnSize = constraintLayout.measuredWidth / COLUMN_COUNT
        val cellSize : Int = (columnSize * CELL_COEFFICIENT).toInt()
        //Get headquarters:
        val headquarters = this.holder.item
        val producableId = headquarters.producableId
        val playerId = headquarters.playerId
        //Create images:
        this.actionImageViewSet.clear()
        this.actionImageViewSet.add(
            BSkirmishBuildHumanBarracksImageView.create(uiGameContext, producableId, playerId)
        )
        this.actionImageViewSet.add(
            BSkirmishBuildHumanTurretImageView.create(uiGameContext, producableId, playerId)
        )
        this.actionImageViewSet.add(
            BSkirmishBuildHumanWallImageView.create(uiGameContext, producableId, playerId)
        )
        if (BHumanTools.countGenerators(this.context, playerId) < BHumanTools.GENERATOR_LIMIT) {
            this.actionImageViewSet.add(
                BSkirmishBuildHumanGeneratorImageView.create(uiGameContext, producableId, playerId)
            )
        }
        if (BHumanTools.countDiffBarracksFactory(this.context, playerId) > 0) {
            this.actionImageViewSet.add(
                BSkirmishBuildHumanFactoryImageView.create(uiGameContext, producableId, playerId)
            )
        }
        if (BHumanTools.countPossibleBuildingUpgrades(this.context, playerId) > 0) {
            this.actionImageViewSet.add(
                BSkirmishUpgradeHumanBuilding.create(uiGameContext, producableId, playerId)
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

    override fun intoPipe() = Pipe()

//    override fun isUnused() = this.unitMap.containsKey(this.holder.uiUnitId)

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
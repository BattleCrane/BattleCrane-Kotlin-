package com.orego.battlecrane.ui.model.std.race.human.unit.building

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanHeadquarters
import com.orego.battlecrane.bc.std.race.human.util.BHumanTools
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder
import com.orego.battlecrane.ui.model.std.race.human.action.building.BHumanBuildBarracksImageView
import com.orego.battlecrane.ui.util.gone
import com.orego.battlecrane.ui.util.show
import org.intellij.lang.annotations.MagicConstant

class BHumanHeadquartersHolder(uiGameContext: BUiGameContext, override val item: BHumanHeadquarters) :
    BUnitHolder(uiGameContext, item) {

    companion object {

        private val COLOR_MAP = mapOf(
            1.toLong() to "blue",
            2.toLong() to "red"
        )
    }

    override val unitView = BUnitHolder.placeImageView(uiGameContext, this.item, this.getPath())

    init {
        val gameContext = uiGameContext.gameContext
        val pipeId = this.item.produceEnableConnection.sourcePipeId
        val pipe = gameContext.pipeline.findPipe(pipeId)
        val node = pipe.findNodeBy { node ->
            node is BHumanHeadquarters.OnProduceEnableNode
        }
        node!!.connectInnerPipe(OnProduceEnableNode(uiGameContext, this).intoPipe())
    }

    private fun getPath() =
        "race/human/unit/headquarters/" +
                "${COLOR_MAP[this.item.playerId]}/" +
                "${this.item.currentHitPoints}.png"

    fun showDescription(uiGameContext: BUiGameContext) {

    }

    @BUnitComponent
    class OnProduceEnableNode(uiGameContext: BUiGameContext, private val holder: BHumanHeadquartersHolder) :
        BNode(uiGameContext.gameContext) {

        companion object {

            @MagicConstant
            private const val MAGIC_PADDING = 5
        }

        /**
         * Context.
         */

        private val animationPipe = uiGameContext.animationPipe

        /**
         * ImageView.
         */

        private val isEnableImageView: ImageView = this.createBackground(uiGameContext)

        private val actionImageViewSet = mutableSetOf<ImageView>()

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnProduceEnablePipe.Event && event.producableId == this.holder.item.producableId) {
                val animation: suspend () -> Unit =
                    if (event.isEnable) {
                        {
                            this.isEnableImageView.show()
                        }
                    } else {
                        { this.isEnableImageView.gone() }
                    }
                this.animationPipe.addAnimation(animation)
            }
            return null
        }

        private fun showActions(uiGameContext: BUiGameContext) {
            //Get right layout:
            val constraintLayout = uiGameContext.uiProvider.rightConstraintLayout
            val constraintLayoutId = constraintLayout.id
            val sizeX = constraintLayout.measuredWidth / BMapController.MAP_SIZE
            val sizeY = constraintLayout.measuredHeight / BMapController.MAP_SIZE
            //Get headquarters:
            val headquarters = this.holder.item
            val producableId = headquarters.producableId
            val playerId = headquarters.playerId
            //Create images:
            this.actionImageViewSet.clear()
            this.actionImageViewSet.add(
                BHumanBuildBarracksImageView.create(uiGameContext, producableId, playerId)
            )
            this.actionImageViewSet.add(BHumanBuildTurretImageView.create(uiGameContext, producableId, playerId))
            this.actionImageViewSet.add(BHumanWallHolder.placeImageView())
            if (BHumanTools.countGenerators(this.context, playerId) < BHumanTools.GENERATOR_LIMIT) {
                this.actionImageViewSet.add(BHumanGeneratorHolder.placeImageView())
            }
            if (BHumanTools.countDiffBarracksFactory(this.context, playerId) > 0) {
                this.actionImageViewSet.add(BHumanFactoryHolder.placeImageView())
            }
            if (BHumanTools.countPossibleBuildingUpgrades(this.context, playerId) > 0) {
                this.actionImageViewSet.add(BHumanUpgradeBuilding.placeImageView)
            }
            var x = 0
            var y = 0
            for (imageView in this.actionImageViewSet) {
                if (x == 3) {
                    x = 0
                    y++
                }
                val constraintParams = ConstraintLayout.LayoutParams(sizeX - MAGIC_PADDING, sizeY - MAGIC_PADDING)
                    .also {
                        it.startToStart = constraintLayoutId
                        it.topToTop = constraintLayoutId
                        it.marginStart = sizeX * x
                        it.topMargin = sizeY * y
                    }
                imageView.layoutParams = constraintParams
                constraintLayout.addView(imageView)
            }
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
    }

    /**
     * Builder.
     */

    class Builder : BUnitHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BUnit) =
            BHumanHeadquartersHolder(uiGameContext, item as BHumanHeadquarters)
    }
}
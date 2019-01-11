package com.orego.battlecrane.ui.model.std.race.human.adjutant

import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.player.BPlayer
import com.orego.battlecrane.bc.std.race.human.adjutant.BHumanAdjutant
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.holder.adjutant.BAdjutantHolder

class BHumanAdjutantHolder(uiGameContext: BUiGameContext, override val item: BHumanAdjutant) :
    BAdjutantHolder(uiGameContext, item) {

    init {
        TurnTimerNode.connect(uiGameContext, this.item)
    }


    @BUnitComponent
    class TurnTimerNode(private val uiContext: BUiGameContext, private val playerId : Long) :
        BNode(uiContext.gameContext) {

        companion object {

            fun connect(uiContext: BUiGameContext, adjutant: BHumanAdjutant) {
                val playerId = adjutant.playerId
                val player = uiContext.gameContext.storage.getHeap(BPlayerHeap::class.java)[playerId]
                val onDestroyNode = TurnTimerNode(uiContext, playerId)
                uiContext.gameContext.pipeline.bindPipeToNode(player.turnTimerNodeId, onDestroyNode.wrapInPipe())
            }
        }

        override fun handle(event: BEvent): BEvent? {
            //TODO: on turn started set progress bar:
//            this.uiProvider.timeProgressBar.min = 0
//            this.uiProvider.timeProgressBar.max = 45

            //While wuthout timer!!!
//            println("AAAAAAA!")
            if (event is BPlayer.TurnTimerNode.TimeLeftEvent && event.playerId == this.playerId) {
                this.uiContext.apply {
                    this.animationPipe.addAnimation {
                        this.uiProvider.timeProgressBar.min = 0
                        this.uiProvider.timeProgressBar.max = 45
                        this.uiProvider.timeProgressBar.progress = event.timeLeft.toInt()
                    }
                    this.uiProvider.timeProgressBar.progress = event.timeLeft.toInt()
                }
            }
            return null
        }
    }

    class Builder : BAdjutantHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BAdjutant) =
            BHumanAdjutantHolder(uiGameContext, item as BHumanAdjutant)
    }
}
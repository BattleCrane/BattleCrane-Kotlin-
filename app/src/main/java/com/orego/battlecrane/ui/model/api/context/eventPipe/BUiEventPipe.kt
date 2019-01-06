package com.orego.battlecrane.ui.model.api.context.eventPipe

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.BPipeline

class BUiEventPipe(gameContext: BGameContext) : BPipeline.OnPipelineWorkFinishedListener {

    init {
        gameContext.pipeline.registerOnPipelineWorkFinishedListener(this)
    }

    private val animationQueue = mutableListOf<BUiEvent>()

    fun addUiEvent(uiEvent : BUiEvent) {
        this.animationQueue += uiEvent
    }

    override fun onPipelineWorkFinished() {
        this.animationQueue.forEach { it.perform() }
        this.animationQueue.clear()
    }
}
package com.orego.battlecrane.ui.model.api.eventPipe

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.BPipeline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BAnimationPipe(gameContext: BGameContext) : BPipeline.OnPipelineWorkFinishedListener {

    init {
        gameContext.pipeline.registerOnPipelineWorkFinishedListener(this)
    }

    private val animationQueue: MutableList<suspend () -> Unit> = mutableListOf()

    fun addAnimation(animation: suspend () -> Unit) {
        this.animationQueue += animation
    }

    override fun onPipelineWorkFinished() {
        GlobalScope.launch(Dispatchers.Main) {
            this@BAnimationPipe.animationQueue.forEach { animation -> animation() }
            this@BAnimationPipe.animationQueue.clear()
        }
    }
}
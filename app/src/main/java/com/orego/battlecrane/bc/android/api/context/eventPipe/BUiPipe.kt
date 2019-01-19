package com.orego.battlecrane.bc.android.api.context.eventPipe

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.BPipeline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BUiPipe(gameContext: BGameContext) : BPipeline.OnPipelineWorkFinishedListener {

    init {
        gameContext.pipeline.registerOnPipelineWorkFinishedListener(this)
    }

    private val animationQueue: MutableList<suspend () -> Unit> = mutableListOf()

    fun addAnimation(animation: suspend () -> Unit) {
        this.animationQueue += animation
    }

    override fun onPipelineWorkFinished() {
        GlobalScope.launch(Dispatchers.Main) {
            this@BUiPipe.animationQueue.forEach { animation -> animation() }
            this@BUiPipe.animationQueue.clear()
        }
    }
}
package com.orego.battlecrane.bc.android.api.context.taskManager

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.BPipeline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BUiTaskManager(gameContext: BGameContext) : BPipeline.OnPipelineWorkFinishedListener {

    private var job: Job? = null

    init {
        gameContext.pipeline.registerOnPipelineWorkFinishedListener(this)
    }

    private val taskQueue: MutableList<suspend () -> Unit> = mutableListOf()

    fun addTask(task: suspend () -> Unit) {
        this.taskQueue += task
    }

    fun performTasks() =
        GlobalScope.launch(Dispatchers.Main) {
            this@BUiTaskManager.taskQueue.forEach { task -> task() }
            this@BUiTaskManager.taskQueue.clear()
        }

    fun cancelTasks() {
        this.job?.cancel()
    }

    override fun onPipelineWorkFinished() {
        this.job = this.performTasks()
    }
}
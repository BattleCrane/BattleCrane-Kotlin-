package com.orego.battlecrane.bc.android.api.util.trigger.enable

import android.view.View
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.heap.BUiUnitHeap
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.util.pipe.BParentPipe
import com.orego.battlecrane.ui.util.gone
import com.orego.battlecrane.ui.util.show

abstract class BUiOnEnableTrigger(protected val uiGameContext: BUiGameContext, protected open val uiUnit: BUiUnit) :
    BNode(uiGameContext.gameContext) {

    private val unitMap = this.context.storage.getHeap(BUiUnitHeap::class.java).objectMap

    protected val isEnableImageView by lazy {
        this.onDraw()
    }

    protected open val uiClickMode by lazy {
        UiClickMode()
    }

    override fun handle(event: BEvent): BEvent? {
        if (this.mathchesEvent(event)) {
            val animation: suspend () -> Unit =
                if (event.isEnable()) {
                    { this.isEnableImageView.show() }
                } else {
                    { this.isEnableImageView.gone() }
                }
            this.uiGameContext.uiTaskManager.addTask(animation)
        }
        return null
    }

    protected abstract fun mathchesEvent(event: BEvent?): Boolean

    protected abstract fun BEvent.isEnable(): Boolean

    protected open fun onClick() {
        this.uiGameContext.uiClickController.pushClickMode(this.uiClickMode)
    }

    protected open fun onDraw(): View {
        val uiProvider = this.uiGameContext.uiProvider
        val applicationContext = uiProvider.applicationContext
        val constraintLayout = uiProvider.mapConstraintLayout
        //Create image unitView:
        val imageView = ImageView(applicationContext)
        imageView.id = View.generateViewId()
        imageView.background = applicationContext.getDrawable(R.color.colorReady)
        imageView.layoutParams = this.uiUnit.unitView.layoutParams
        imageView.gone()
        imageView.setOnClickListener { this.onClick() }
        constraintLayout.addView(imageView)
        return imageView
    }

    protected abstract fun onDrawActions()

    override fun intoPipe() = Pipe()

    override fun isFinished() = !this.unitMap.containsKey(this.uiUnit.uiUnitId)

    /**
     * Pipe.
     */

    inner class Pipe : BParentPipe(this)

    /**
     * Click mode.
     */

    open inner class UiClickMode : BUiUnit.UiClickMode(this.uiGameContext, this.uiUnit) {

        override fun onStartClickMode() {
            this.unit.setDescription(this@BUiOnEnableTrigger.uiGameContext)
            this@BUiOnEnableTrigger.onDrawActions()
        }
    }
}
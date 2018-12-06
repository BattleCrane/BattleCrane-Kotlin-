package com.orego.battlecrane.ui.model.api.view.tool

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bc.api.manager.mapManager.point.BPoint
import com.orego.battlecrane.ui.model.api.view.BView
import com.orego.battlecrane.ui.util.setImageById
import org.intellij.lang.annotations.MagicConstant

open class BToolView(entity: BTool, measuredCellSize: Int, context: Context) : BView<BToolView.BTool>(entity) {

    companion object {

        @MagicConstant
        private const val COEFFICIENT = 4
    }

    lateinit var position: BPoint

    final override val displayedView: ConstraintLayout

    init {
        val toolImage = ImageView(context)
        toolImage.id = View.generateViewId()
        toolImage.layoutParams = ConstraintLayout.LayoutParams(measuredCellSize, measuredCellSize)
        toolImage.setImageById(context, entity.imageId)
        val toolName = TextView(context)
        toolName.id = View.generateViewId()
        toolName.text = entity.name
        toolName.layoutParams = ConstraintLayout.LayoutParams(measuredCellSize, measuredCellSize / COEFFICIENT)
        this.displayedView = ConstraintLayout(context)
        this.displayedView.id = View.generateViewId()
        this.displayedView.layoutParams = ConstraintLayout.LayoutParams(measuredCellSize, measuredCellSize)
        this.displayedView.addView(toolImage)
        this.displayedView.addView(toolName)
    }

    data class BTool(val name: String, val description: String, val imageId: Int)
}
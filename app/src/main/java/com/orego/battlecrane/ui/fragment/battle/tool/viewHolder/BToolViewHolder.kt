package com.orego.battlecrane.ui.fragment.battle.tool.viewHolder

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.ui.fragment.battle.holder.BViewHolder
import com.orego.battlecrane.ui.util.setImageById
import org.intellij.lang.annotations.MagicConstant

class BToolViewHolder(entity: BTool, measuredCellSide: Int, context: Context) : BViewHolder<BTool>(entity) {

    companion object {

        @MagicConstant
        private const val COEFFICIENT = 4
    }

    override val displayedView: ConstraintLayout

    init {
        val toolImage = ImageView(context)
        toolImage.id = View.generateViewId()
        toolImage.layoutParams = ConstraintLayout.LayoutParams(measuredCellSide, measuredCellSide)
        toolImage.setImageById(context, entity.imageId)
        val toolName = TextView(context)
        toolName.id = View.generateViewId()
        toolName.text = entity.name
        toolName.layoutParams = ConstraintLayout.LayoutParams(measuredCellSide, measuredCellSide / COEFFICIENT)
        this.displayedView = ConstraintLayout(context)
        this.displayedView.id = View.generateViewId()
        this.displayedView.layoutParams = ConstraintLayout.LayoutParams(measuredCellSide, measuredCellSide)
        this.displayedView.addView(toolImage)
        this.displayedView.addView(toolName)
    }
}
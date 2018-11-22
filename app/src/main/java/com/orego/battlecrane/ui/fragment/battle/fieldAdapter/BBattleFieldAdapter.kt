package com.orego.battlecrane.ui.fragment.battle.fieldAdapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.orego.battlecrane.R
import com.orego.battlecrane.repository.game.manager.field.BBattleField

class BBattleFieldAdapter (val context: Context, private val battleField : BBattleField): BaseAdapter() {

    override fun getCount() = this.battleField.cellCount

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView = ImageView(this.context)
        imageView.setImageResource(R.drawable.ic_action_name)
        imageView.layoutParams = ViewGroup.LayoutParams(14, 14)
        return imageView
    }
}
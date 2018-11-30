package com.orego.battlecrane.ui.fragment.battle.tool.unit

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.orego.battlecrane.R
import com.orego.battlecrane.ui.fragment.battle.tool.BToolInfo
import com.orego.battlecrane.ui.util.setImageById

class BUnitToolGridViewAdapter(private val context: Context) : BaseAdapter() {

    private val toolList = mutableListOf<BToolInfo>()

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount() = this.toolList.size

    override fun getItem(position: Int) = this.toolList[position]

    override fun getItemId(position: Int) = position.toLong()

    fun refreshTools(tools : List<BToolInfo>?) {
        this.toolList.clear()
        tools?.let { this.toolList.addAll(it) }
        this.notifyDataSetChanged()
    }

    fun addTool(tool: BToolInfo?) {
        tool?.let { this.toolList.add(it) }
    }

    fun removeTool(tool: BToolInfo?) {
        tool?.let { this.toolList.add(it) }
    }


    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: this.inflater.inflate(R.layout.view_unit_tool, null)
        val imageView = view.findViewById<ImageView>(R.id.view_unit_tool_image_view)
        val textView = view.findViewById<TextView>(R.id.view_unit_tool_text_view)
        val unitChoiceInfo = this.toolList[position]
        imageView.setImageById(this.context, unitChoiceInfo.imageId)
        textView.text = unitChoiceInfo.name
        return view
    }
}
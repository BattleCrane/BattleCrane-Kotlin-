package com.orego.battlecrane.ui.viewModel

import androidx.lifecycle.ViewModel
import bcApi.unit.BUnit
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.model.map.BUnitViewHolder
import com.orego.battlecrane.ui.model.tool.BTool
import com.orego.battlecrane.ui.model.tool.BToolViewHolder

class BFactoryViewModel : ViewModel() {

    val mapFactory = BRender.ViewHolderFactory<BUnit, BUnitViewHolder>()

    val buildToolFactory = BRender.ViewHolderFactory<BTool, BToolViewHolder>()

    val trainToolFactory = BRender.ViewHolderFactory<BTool, BToolViewHolder>()

    val reinforcementToolFactory = BRender.ViewHolderFactory<BTool, BToolViewHolder>()
}
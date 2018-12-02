package com.orego.battlecrane.ui.fragment.battle.render.tool.unit

import bcApi.unit.BUnit
import com.orego.battlecrane.ui.fragment.battle.render.tool.BToolRender

abstract class BUnitToolRender : BToolRender<BUnit>(COLUMN_COUNT, ROW_COUNT) {

    companion object {

        private const val COLUMN_COUNT = 2

        private const val ROW_COUNT = 3
    }
}
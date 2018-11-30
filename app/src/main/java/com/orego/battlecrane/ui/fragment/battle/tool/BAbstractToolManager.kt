package com.orego.battlecrane.ui.fragment.battle.tool

import com.orego.battlecrane.bcApi.manager.playerManager.BPlayerManager

abstract class BAbstractToolManager(private val playerManager: BPlayerManager) {

    protected val currentPlayer
        get() = this.playerManager.currentPlayer
}
package com.orego.battlecrane.bc.api.model.contract

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction

interface BProducable {

    var isProduceEnable : Boolean

    fun pushProduceActions(context: BGameContext, owner : BPlayer)
}
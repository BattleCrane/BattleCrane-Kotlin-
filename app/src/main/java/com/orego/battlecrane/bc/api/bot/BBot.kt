package com.orego.battlecrane.bc.api.bot

import com.orego.battlecrane.bc.api.model.action.BAction

interface BBot {

    fun calc(): List<BAction>
}
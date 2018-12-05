package com.orego.battlecrane.bc.api.model.action

import com.orego.battlecrane.bc.api.manager.BGameContext

abstract class BAction(gameContext : BGameContext) {

    abstract fun make() :Boolean
}
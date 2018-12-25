package com.orego.battlecrane.bc.api.model.contract

import com.orego.battlecrane.bc.api.model.action.BAction

interface BAttackable {

    var damage: Int

    var isAttackEnable : Boolean

    fun getAttackAction() : BAction?
}
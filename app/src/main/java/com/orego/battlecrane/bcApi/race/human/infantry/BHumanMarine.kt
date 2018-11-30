package com.orego.battlecrane.bcApi.race.human.infantry

import com.orego.battlecrane.bcApi.unit.BUnit
import com.orego.battlecrane.bcApi.util.BAttackable
import com.orego.battlecrane.bcApi.util.BHealthable

abstract class BHumanMarine : BUnit(), BHealthable, BAttackable {



    final override val verticalSide: Int = 1
}
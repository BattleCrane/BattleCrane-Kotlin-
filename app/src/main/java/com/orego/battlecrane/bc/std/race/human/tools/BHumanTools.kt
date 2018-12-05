package com.orego.battlecrane.bc.std.race.human.tools

import com.orego.battlecrane.bc.api.model.tools.BTools
import com.orego.battlecrane.bc.std.race.human.building.implementation.*
import com.orego.battlecrane.bc.std.race.human.infantry.implementation.BHumanMarine
import com.orego.battlecrane.bc.std.race.human.upgrade.BHumanUpgrade
import com.orego.battlecrane.bc.std.race.human.vehicle.implementation.BHumanTank

class BHumanTools : BTools(

    mutableListOf(
        BHumanBarracks::class.java,
        BHumanGenerator::class.java,
        BHumanFactory::class.java,
        BHumanTurret::class.java,
        BHumanWall::class.java,
        BHumanUpgrade::class.java
    ),

    mutableListOf(
        BHumanMarine.Marine1::class.java,
        BHumanMarine.Marine2::class.java,
        BHumanMarine.Marine3::class.java,
        BHumanTank.Tank1::class.java,
        BHumanTank.Tank2::class.java,
        BHumanTank.Tank3::class.java
    ),

    //TODO MAKE BONUS:
    mutableListOf()
)
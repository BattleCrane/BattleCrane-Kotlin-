package com.orego.battlecrane.bcApi.race.human.tools

import com.orego.battlecrane.bcApi.model.tools.BTools
import com.orego.battlecrane.bcApi.race.human.building.implementation.*
import com.orego.battlecrane.bcApi.race.human.infantry.implementation.BHumanMarine
import com.orego.battlecrane.bcApi.race.human.vehicle.implementation.BHumanTank

class BHumanTools : BTools(

    mutableListOf(
        BHumanBarracks::class.java,
        BHumanGenerator::class.java,
        BHumanHeadquarters::class.java,
        BHumanFactory::class.java,
        BHumanTurret::class.java,
        BHumanWall::class.java
    ),

    mutableListOf(
        BHumanMarine.Marine1::class.java,
        BHumanMarine.Marine2::class.java,
        BHumanMarine.Marine3::class.java,
        BHumanTank.BHumanTank1::class.java,
        BHumanTank.BHumanTank2::class.java,
        BHumanTank.BHumanTank3::class.java
    ),

    mutableListOf()
)
package com.orego.battlecrane.bc.std.race.human.unit.building.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.api.model.entity.property.BLevelable
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding

class BHumanGenerator(context: BGameContext, playerId: Long, x : Int, y : Int) :
    BHumanBuilding(context, playerId, x, y),
    BHitPointable,
    BLevelable {

    companion object {

        private const val DEFAULT_HEIGTH = 2

        private const val DEFAULT_WIDTH = 2

        private const val DEFAULT_MAX_HEALTH = 1

        private const val DEFAULT_LEVEL = 1

        private const val DEFAULT_MAX_LEVEL = 3
    }

    /**
     * Properties.
     */

    override val height = DEFAULT_HEIGTH

    override val width = DEFAULT_WIDTH

    override var currentHitPoints = DEFAULT_MAX_HEALTH

    override var maxHitPoints = DEFAULT_MAX_HEALTH

    override var currentLevel = DEFAULT_LEVEL

    override var maxLevel = DEFAULT_MAX_LEVEL

    /**
     * Id.
     */

    override val hitPointableId: Long

    override val levelableId: Long

    init {
        val generator = context.contextGenerator
        this.hitPointableId =
    }
}
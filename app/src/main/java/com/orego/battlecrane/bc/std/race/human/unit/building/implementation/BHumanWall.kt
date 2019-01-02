package com.orego.battlecrane.bc.std.race.human.unit.building.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding

class BHumanWall(context: BGameContext, plyerId: Long, x : Int, y : Int) :
    BHumanBuilding(context, plyerId, x, y), BHitPointable {

    companion object {

        private const val HEIGHT = 1

        private const val WIDTH = 1

        private const val MAX_HIT_POINTS = 4
    }

    /**
     * Properties.
     */

    override val height = HEIGHT

    override val width = WIDTH

    override var currentHitPoints = MAX_HIT_POINTS

    override var maxHitPoints = MAX_HIT_POINTS

    /**
     * Id.
     */

    override val hitPointableId = context.contextGenerator.getIdGenerator(BHitPointable::class.java).generateId()
}
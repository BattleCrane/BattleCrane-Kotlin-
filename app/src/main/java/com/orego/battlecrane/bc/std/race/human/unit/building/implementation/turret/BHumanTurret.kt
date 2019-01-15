package com.orego.battlecrane.bc.std.race.human.unit.building.implementation.turret

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.property.attackable.BAttackable
import com.orego.battlecrane.bc.api.model.property.hitPointable.BHitPointable
import com.orego.battlecrane.bc.api.model.property.levelable.BLevelable
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding

/**
 * Attacks enemies in radius.
 */

class BHumanTurret(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BHumanBuilding(context, playerId, x, y), BHitPointable,
    BLevelable,
    BAttackable {

    companion object {

        const val HEIGHT = 1

        const val WIDTH = 1

        const val LEVEL_1_MAX_HIT_POINTS = 2

        const val LEVEL_2_MAX_HIT_POINTS = 4

        const val FIRST_LEVEL = 1

        const val SECOND_LEVEL = 2

        const val MAX_LEVEL =
            SECOND_LEVEL

        const val DAMAGE = 1

        const val ALWAYS_ATTACK_ENABLE = false

        const val RADIUS_ATTACK = 2
    }

    /**
     * Id.
     */

    override val hitPointableId: Long

    override val levelableId: Long

    override val attackableId: Long

    init {
        val generator = context.contextGenerator
        this.hitPointableId = generator.getIdGenerator(BHitPointable::class.java).generateId()
        this.levelableId = generator.getIdGenerator(BLevelable::class.java).generateId()
        this.attackableId = generator.getIdGenerator(BAttackable::class.java).generateId()
    }

    /**
     * Property.
     */

    override val height =
        HEIGHT

    override val width =
        WIDTH

    override var currentHitPoints =
        LEVEL_1_MAX_HIT_POINTS

    override var maxHitPoints =
        LEVEL_1_MAX_HIT_POINTS

    override var currentLevel =
        FIRST_LEVEL

    override var maxLevel =
        MAX_LEVEL

    override var damage =
        DAMAGE

    override var isAttackEnable =
        ALWAYS_ATTACK_ENABLE

    var radiusAttack =
        RADIUS_ATTACK

    /**
     * Builder.
     */

    open class Builder {

        fun build(context: BGameContext, playerId: Long, x: Int, y: Int) =
            BHumanTurret(context, playerId, x, y)
    }
}
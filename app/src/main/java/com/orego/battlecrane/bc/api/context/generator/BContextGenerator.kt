package com.orego.battlecrane.bc.api.context.generator

import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.bc.api.model.entity.property.BAttackable
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.api.model.entity.property.BLevelable
import com.orego.battlecrane.bc.api.model.entity.property.BProducable
import com.orego.battlecrane.bc.api.model.player.BPlayer

@BContextComponent
class BContextGenerator {

    val generatorMap = mutableMapOf<Class<*>, IdGenerator>(
        BPlayer::class.java to IdGenerator(1),
        BUnit::class.java to IdGenerator(0),
        BAttackable::class.java to IdGenerator(0),
        BHitPointable::class.java to IdGenerator(0),
        BLevelable::class.java to IdGenerator(0),
        BProducable::class.java to IdGenerator(0),
        BPipe::class.java to IdGenerator(0),
        BNode::class.java to IdGenerator(0),
        BAdjutant::class.java to IdGenerator(1)
    )

    fun getIdGenerator(clazz: Class<*>) = this.generatorMap[clazz]!!

    class IdGenerator(private var counter: Long) {

        fun generateId() = this.counter++
    }
}
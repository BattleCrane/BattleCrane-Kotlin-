package com.orego.battlecrane.bc.engine.api.util.builder

import com.orego.battlecrane.bc.engine.api.context.BGameContext

/**
 * Flexible item builder.
 */

abstract class BBuilder<T> {

    private val configurations = mutableListOf<(BGameContext, T) -> Unit>()

    protected abstract fun onCreate(context: BGameContext) : T

    fun build(context: BGameContext): T {
        val model = this.onCreate(context)
        this.configurations.forEach { plugin -> plugin(context, model) }
        return model
    }

    fun addConfiguration(configure: (BGameContext, T) -> Unit) {
        this.configurations += configure
    }

    fun removeConfiguration(configure: (BGameContext, T) -> Unit) {
        this.configurations -= configure
    }
}
package com.orego.battlecrane.bc.engine.api.util.common

fun <T, R> List<T>.mapWithFilter(
    filterFunc: (T) -> Boolean,
    transformFunc: (T) -> R
) = this.filter { filterFunc(it) }.map { transformFunc(it) }

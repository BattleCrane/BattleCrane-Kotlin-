package com.orego.battlecrane.bc.api.util

object BIdGenerator {

    private var unitIdCounter : Long = 0

    private var actionIdCounter : Long = 0

    fun generateUnitId() = unitIdCounter++

    fun generateActionId() = actionIdCounter++
}
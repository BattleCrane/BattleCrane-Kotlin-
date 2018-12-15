package com.orego.battlecrane.bc.api.context.pipeline

data class BEvent(val name : String?, val any : Any?)

fun isValidEvent(event : BEvent?) = event?.name != null && event.any != null

object BEventContract {

    const val CREATE = "CREATE"
}
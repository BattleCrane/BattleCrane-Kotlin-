package com.orego.battlecrane.bc.api.context.eventPipeline.model.annotation

@Retention(value = AnnotationRetention.SOURCE)
@Target(allowedTargets = [AnnotationTarget.CLASS])
annotation class BPlayerComponent

@Retention(value = AnnotationRetention.SOURCE)
@Target(allowedTargets = [AnnotationTarget.CLASS])
annotation class BUnitComponent
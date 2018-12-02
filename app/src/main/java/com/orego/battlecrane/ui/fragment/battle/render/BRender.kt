package com.orego.battlecrane.ui.fragment.battle.render

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

abstract class BRender<K, V> {

    private var isInstalled = false

    protected lateinit var context: Context

    protected lateinit var factory: ViewHolderFactory<K, V>

    protected lateinit var constraintLayout: ConstraintLayout

    protected val constraintSet = ConstraintSet()

    protected val temporaryViewHolderList: MutableList<V> = mutableListOf()

    abstract fun draw()

    fun install(constraintLayout: ConstraintLayout, factory: ViewHolderFactory<K, V>, context: Context) {
        if (!this.isInstalled) {
            this.factory = factory
            this.constraintLayout = constraintLayout
            this.context = context
        }
        this.draw()
    }

    class ViewHolderFactory<K, V> {

        private val builderMap = mutableMapOf<Class<out K>, ViewHolderBuilder<K, V>>()

        fun addViewHolderBuilder(type: Class<out K>, builder: ViewHolderBuilder<K, V>) {
            val builderMap = this.builderMap
            if (!builderMap.containsKey(type)) {
                builderMap[type] = builder
            } else {
                throw IllegalStateException("The type is added")
            }
        }

        fun build(unit: K, measuredCellSide: Int, context: Context, unitClazz: Class<out K>): V {
            val builder = this.builderMap[unitClazz]
            if (builder != null) {
                return builder.build(unit, measuredCellSide, context)
            } else {
                throw IllegalStateException("Not supported type!")
            }
        }
    }

    interface ViewHolderBuilder<K, V> {

        fun build(unit: K, measuredCellSide: Int, context: Context): V
    }
}
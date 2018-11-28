package com.orego.battlecrane.ui.fragment.battle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.R
import com.orego.battlecrane.ui.fragment.BFragment
import com.orego.battlecrane.ui.fragment.battle.mapRender.BBattleMapRender
import com.orego.battlecrane.ui.fragment.battleLoading.BBattleLoadingFragment
import kotlinx.android.synthetic.main.fragment_battle.*

class BBattleFragment : BFragment() {

    override val presenter = Presenter()

    override fun onCreateContentView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View? = i
        .inflate(R.layout.fragment_battle, c, false)!!

    override fun onContentViewCreated() {
        //Actual code:
    }

    override fun onStart() {
        super.onStart()
        this.presenter.drawMap(this.fragment_battle_map_constraint_layout)
    }

    inner class Presenter : BFragment.BPresenter() {

        private val mapRender by lazy {
            BBattleMapRender(
                this.manager.gameManager.battleMap,
                this@BBattleFragment.fragment_battle_map_constraint_layout,
                this@BBattleFragment.context!!
            )
        }


        fun drawMap(constraintLayout: ConstraintLayout) {
            val observer = constraintLayout.viewTreeObserver
            observer.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

                override fun onGlobalLayout() {
                    observer.removeOnGlobalLayoutListener(this)
                    this@Presenter.mapRender.draw()
                }
            })
        }
    }
}
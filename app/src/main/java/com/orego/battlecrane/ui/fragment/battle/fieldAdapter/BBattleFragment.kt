package com.orego.battlecrane.ui.fragment.battle.fieldAdapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orego.battlecrane.R
import com.orego.battlecrane.ui.fragment.BFragment
import kotlinx.android.synthetic.main.fragment_battle.*

class BBattleFragment : BFragment() {

    override fun onCreateContentView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View? = i
        .inflate(R.layout.fragment_battle, c, false)!!

    override fun onContentViewCreated() {
        this.battle_field_view.adapter = BBattleFieldAdapter(this.context!!, this.presenter.manager.battleField)
    }
}
package com.orego.battlecrane.core

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.orego.battlecrane.R
import com.orego.battlecrane.ui.fragment.BFragment
import com.orego.battlecrane.ui.fragment.battle.fieldAdapter.BBattleFragment

class BActivity : AppCompatActivity() {

    private val app
        get() = this.application as BApplication

    private var activityController: BActivityController?
        get() = this.app.activityController
        set(activityController) {
            this.app.activityController = activityController
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        if (this.activityController == null) {
            this.activityController = BActivityController()
        }
        this.activityController!!.requestFragment(BBattleFragment::class.java, this.supportFragmentManager)
    }

    /**
     * Controls application fragments.
     */

    class BActivityController {

        private lateinit var displayedFragment: BFragment

        private val fragmentMap = mutableMapOf(
            BBattleFragment::class.java to BBattleFragment()
        )

        fun requestFragment(
            clazz: Class<out BFragment>,
            manager: FragmentManager,
            bundle: Bundle? = null
        ) {
            val fragment = this.fragmentMap[clazz]
            if (fragment != null) {
                fragment.arguments = bundle
                val transaction: FragmentTransaction = manager.beginTransaction()
                transaction.replace(R.id.fragment, fragment).commit()
                this.displayedFragment = fragment
            } else {
                throw IllegalArgumentException("There isn't fragment class!")
            }
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
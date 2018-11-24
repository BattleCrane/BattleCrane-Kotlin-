package com.orego.battlecrane.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.orego.battlecrane.core.BActivity
import com.orego.battlecrane.core.BApplication
import com.orego.battlecrane.bcApi.manager.BGameManager
import com.orego.battlecrane.repository.manager.BRepositoryManager

/**
 * @author Игорь Гулькин 14.07.2018
 *
 * MFragment удобный абстрактный класс,
 * который загружается 1 раз. При открытии фрагмента
 * загружать повторно не нужно.
 *
 * SAConvenientFragment имеет четырёх-фазовый конструктор:
 *
 * 1.) Создание фрагмента (родной Android конструктор)
 * 2.) onCreateContentView вызывается до развертки контента.
 * 3.) onContentViewCreated вызывается после развертки. тут устанавливайте mListeners.
 */

abstract class BFragment : Fragment() {

    protected open val presenter: BPresenter = BPresenter()

    /**
     * Вызывается после создания конструктора для фрагмента.
     */

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View? {
        if (!this.presenter.wasCreated) {
            super.onCreate(b)
            this.presenter.root = this.onCreateContentView(i, c, b)
        } else {
            this.presenter.root = this.onBindContentView(i, c, b)
        }
        return this.presenter.root
    }

    /**
     * Вызывается после onCreateView.
     */

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!this.presenter.wasCreated) {
            this.onContentViewCreated()
            this.presenter.wasCreated = true
        } else {
            this.onContentViewBound()
        }
    }

    /**
     * Реализуйте развертку контента в методе onCreateContentView
     * и верните корневой View фрагмента.
     * Делайте всё также как и при onCreateView.
     * Вызывать super.onCreate() не нужно.
     */

    abstract fun onCreateContentView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View?

    /**
     * Вызывается при повторной загрузке фрагмента.
     */

    open fun onBindContentView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View? = this.presenter
        .root

    /**
     * Вызывается внутри onViewCreated.
     */

    open fun onContentViewCreated() {}

    open fun onContentViewBound() {}

    open fun onBackPressed() {
        (activity!! as BActivity).onBackPressed()
    }

    final override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        this.presenter.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    open inner class BPresenter {

        var wasCreated = false

        var root: View? = null

        val manager: BRepositoryManager
            get() = (this@BFragment.activity!!.application as BApplication).manager

        fun replaceFragment(clazz: Class<out BFragment>, bundle: Bundle? = null) {
            val activity = this@BFragment.activity!!
            val application = activity.application as BApplication
            val activityContainer = application.activityController
            activityContainer!!.requestFragment(clazz, activity.supportFragmentManager, bundle)
        }

        fun makeToast(message: String) {
            Toast.makeText(this@BFragment.context, message, Toast.LENGTH_SHORT).show()
        }

        open fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
        }
    }
}
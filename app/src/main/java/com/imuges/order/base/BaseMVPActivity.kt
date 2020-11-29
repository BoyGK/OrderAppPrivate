package com.imuges.order.base

import android.os.Bundle


/**
 * @author BGQ
 * MVP基类
 */
abstract class BaseMVPActivity : BasePermissionActivity(), IBaseView {

    companion object {
        const val DEFAULT = "presenter_default"
    }

    /**
     * 活动绑定的presenter
     */
    private lateinit var presenters: Map<String, BasePresenter<out IBaseView>>

    /**
     * 只有一个presenter时有用
     */
    private val defaultPresenter by lazy {
        if (presenters.isEmpty()) {
            throw RuntimeException("presenters is empty.")
        }
        presenters[DEFAULT]
    }

    /**
     * 初始化presenter
     */
    abstract fun getPresenterMap(): Map<String, BasePresenter<out IBaseView>>

    abstract fun onCreate()

    fun <T : BasePresenter<out IBaseView>> defaultPresenter(): T {
        return defaultPresenter as T
    }

    fun <T : BasePresenter<out IBaseView>> presenter(key: String): T {
        if (presenters[key] == null) {
            throw RuntimeException("key is not found in presenterMap.")
        }
        return presenters[key] as T
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenters = getPresenterMap()
        for ((_, value) in presenters) {
            value.attachView(this)
        }
        onCreate()
        for ((_, value) in presenters) {
            value.onViewCreate()
        }
    }

    override fun onStart() {
        super.onStart()
        for (presenter in presenters) {
            presenter.value.onViewStart()
        }
    }

    override fun onRestart() {
        super.onRestart()
        for (presenter in presenters) {
            presenter.value.onViewReStart()
        }
    }

    override fun onResume() {
        super.onResume()
        for (presenter in presenters) {
            presenter.value.onViewResume()
        }
    }

    override fun onPause() {
        super.onPause()
        for (presenter in presenters) {
            presenter.value.onViewPause()
        }
    }

    override fun onStop() {
        super.onStop()
        for (presenter in presenters) {
            presenter.value.onViewStop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        for (presenter in presenters) {
            presenter.value.onViewDestroy()
            presenter.value.detachView()
        }
    }
}
package com.nullpt.base.framework

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

    /**
     * 初始化回调
     */
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
        for ((_, value) in presenters) {
            value.onViewStart()
        }
    }

    override fun onRestart() {
        super.onRestart()
        for ((_, value) in presenters) {
            value.onViewReStart()
        }
    }

    override fun onResume() {
        super.onResume()
        for ((_, value) in presenters) {
            value.onViewResume()
        }
    }

    override fun onPause() {
        super.onPause()
        for ((_, value) in presenters) {
            value.onViewPause()
        }
    }

    override fun onStop() {
        super.onStop()
        for ((_, value) in presenters) {
            value.onViewStop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        for ((_, value) in presenters) {
            value.onViewDestroy()
            value.detachView()
        }
    }
}
package com.nullpt.base.framework


/**
 * @author BGQ
 * MVP基类
 */
abstract class BaseMVPFragment : BaseFragment(), IBaseView {

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
    abstract fun initView()

    fun <T : BasePresenter<out IBaseView>> defaultPresenter(): T {
        return defaultPresenter as T
    }

    fun <T : BasePresenter<out IBaseView>> presenter(key: String): T {
        if (presenters[key] == null) {
            throw RuntimeException("key is not found in presenterMap.")
        }
        return presenters[key] as T
    }

    final override fun onCreate() {
        presenters = getPresenterMap()
        for ((_, value) in presenters) {
            value.attachView(this)
        }
    }

    final override fun onCreateView() {
        initView()
        for ((_, value) in presenters) {
            value.onViewCreate()
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
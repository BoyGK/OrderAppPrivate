package com.nullpt.base.framework

/**
 * @author BGQ
 * Presenter基类
 */
abstract class BasePresenter<V : IBaseView> {

    var view: V? = null

    fun attachView(v: IBaseView) {
        view = v as V
    }

    fun detachView() {
        view = null
    }

    abstract fun onViewCreate()

    open fun onViewStart() {}

    open fun onViewReStart() {}

    open fun onViewResume() {}

    open fun onViewPause() {}

    open fun onViewStop() {}

    open fun onViewDestroy() {}
}
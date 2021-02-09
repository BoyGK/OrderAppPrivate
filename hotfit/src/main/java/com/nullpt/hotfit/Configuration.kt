package com.nullpt.hotfit

import com.nullpt.base.app.MainApplication

/**
 * @author BGQ
 * 参数配置
 */
object Configuration {

    /**
     * 热修源代码指定路径
     */
    val HOTFIT_SOURCE: String
        get() = MainApplication.instance.cacheDir.absolutePath + "/hotfit/hotfit.dex"

}
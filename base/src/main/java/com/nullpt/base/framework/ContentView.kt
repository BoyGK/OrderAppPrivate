package com.nullpt.base.framework

import androidx.annotation.LayoutRes

/**
 * 界面资源布局注解
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ContentView(@LayoutRes val layoutId: Int)
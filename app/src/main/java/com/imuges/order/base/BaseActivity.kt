package com.imuges.order.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author BGQ
 * Activity基类
 */
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val annotation = javaClass.getAnnotation(ContentView::class.java) ?: return
        setContentView(annotation.layoutId)
    }

}
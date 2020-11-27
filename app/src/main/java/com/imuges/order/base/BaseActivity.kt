package com.imuges.order.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.imuges.order.expan.activityResult

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //注册扩展方法
        activityResult(requestCode, resultCode, data)
    }

    /**
     * 通用提示
     */
    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}
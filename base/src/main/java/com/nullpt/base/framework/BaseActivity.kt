package com.nullpt.base.framework

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nullpt.base.expan.activityResult

/**
 * @author BGQ
 * Activity基类
 */
open class BaseActivity : AppCompatActivity() {

    /**
     * 组件化注解使用无法传入非确定常量,采用此方式
     */
    open fun getLayoutId(): Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val annotation = javaClass.getAnnotation(ContentView::class.java)
        if (annotation != null) {
            setContentView(annotation.layoutId)
        } else if (getLayoutId() != 0) {
            setContentView(getLayoutId())
        }
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
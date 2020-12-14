package com.nullpt.base.framework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * @author BGQ
 * Fragment基类
 */
abstract class BaseFragment : Fragment() {

    abstract fun onCreate()

    abstract fun onCreateView()

    /**
     * 组件化注解使用无法传入非确定常量,采用此方式
     */
    open fun getLayoutId(): Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val annotation = javaClass.getAnnotation(ContentView::class.java)
        return when {
            annotation != null -> {
                inflater.inflate(annotation.layoutId, container, false)
            }
            getLayoutId() != 0 -> {
                inflater.inflate(getLayoutId(), container, false)
            }
            else -> null
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onCreate()
        view ?: return
        onCreateView()
    }

    /**
     * 通用提示
     */
    fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}
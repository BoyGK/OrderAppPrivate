package com.nullpt.hotfit

import android.app.Application
import dalvik.system.PathClassLoader

/**
 * @author BGQ
 * 当爸爸的方式，类热修
 *
 * 实现思路：当爹，小问题表较多，需要完整的dex
 * dex文件需要完整的包名及类运行相关类；
 * 出现错误：
 * java.lang.NoClassDefFoundError
 * java.lang.ClassNotFoundException
 * Didn't find class "kotlin.jvm.internal.Intrinsics"
 */
object Hotfit2 {

    fun check(application: Application) {

        //获取loadApk
        val loadApkField = ReflectUtil.getPublicObjectWithParent(application, "mLoadedApk")
        loadApkField ?: throw RuntimeException("Hotfit separateDexPathList mLoadedApk is no exist!")

        //获取PathClassLoader
        val classLoaderField = ReflectUtil.getObject(loadApkField, "mClassLoader")
        classLoaderField
            ?: throw RuntimeException("Hotfit separateDexPathList mClassLoader is no exist!")

        val classLoaderParent = (classLoaderField as ClassLoader).parent

        //创建补丁加载类，并且设置parent为程序加载器的父类
        val hotfitClassLoader =
            PathClassLoader(Configuration.HOTFIT_SOURCE, null, classLoaderParent)

        //设置程序加载器的父类为补丁加载器
        //PathClassLoader->BaseDexClassLoader->ClassLoader
        val classLoaderClass = classLoaderField.javaClass.superclass.superclass
        try {
            val classLoaderParentField = classLoaderClass.getDeclaredField("parent")
            classLoaderParentField.isAccessible = true
            classLoaderParentField.set(classLoaderField, hotfitClassLoader)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }

}
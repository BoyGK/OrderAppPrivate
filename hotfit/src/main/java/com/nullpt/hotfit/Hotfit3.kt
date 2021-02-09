package com.nullpt.hotfit

import android.app.Application
import dalvik.system.PathClassLoader

/**
 * @author BGQ
 * 当儿子的方式，类热修
 *
 * 实现思路：当儿子
 *
 * 推断问题：
 * 显而易见存在问题，根据类加载流程，父类优先与子类加载，所以儿子根本加载不到
 * 猜测条件需要和Hotfit2相同
 */
object Hotfit3 {

    fun check(application: Application) {

        //获取loadApk
        val loadApk = ReflectUtil.getPublicObjectWithParent(application, "mLoadedApk")
        loadApk ?: throw RuntimeException("Hotfit separateDexPathList mLoadedApk is no exist!")

        //获取PathClassLoader
        val classLoader = ReflectUtil.getObject(loadApk, "mClassLoader")
        classLoader
            ?: throw RuntimeException("Hotfit separateDexPathList mClassLoader is no exist!")

        //创建补丁加载类，并且设置parent为程序加载器
        val hotfitClassLoader =
            PathClassLoader(Configuration.HOTFIT_SOURCE, null, null)

        /*
        测试提前把MainActivity加载出来,然后再设置他的父类
        这样就可以在自己的缓存中加载，而不是去父类
         */
//        hotfitClassLoader.loadClass("com.imuges.order.activity.MainActivity")
//        val classLoaderClass = hotfitClassLoader.javaClass.superclass.superclass
//        try {
//            val classLoaderParentField = classLoaderClass.getDeclaredField("parent")
//            classLoaderParentField.isAccessible = true
//            classLoaderParentField.set(hotfitClassLoader, classLoader)
//        } catch (e: NoSuchFieldException) {
//            e.printStackTrace()
//        } catch (e: IllegalAccessException) {
//            e.printStackTrace()
//        }

        //把补丁加载器设置给loadApk
        ReflectUtil.setObject(loadApk, "mClassLoader", hotfitClassLoader)
    }

}
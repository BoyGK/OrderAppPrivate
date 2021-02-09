package com.nullpt.hotfit

import android.app.Application
import dalvik.system.DexClassLoader

/**
 * @author BGQ
 * 合并elements方式，类热修
 *
 * 实现思路：合并PathList$Element[]
 * ok
 */
object Hotfit {

    /**
     * 入口方法
     */
    fun check(application: Application) {

        //获取loadApk
        val loadApkField = ReflectUtil.getPublicObjectWithParent(application, "mLoadedApk")
        loadApkField ?: throw RuntimeException("Hotfit separateDexPathList mLoadedApk is no exist!")

        //获取PathClassLoader
        val classLoaderField = ReflectUtil.getObject(loadApkField, "mClassLoader")
        classLoaderField
            ?: throw RuntimeException("Hotfit separateDexPathList mClassLoader is no exist!")

        //获取pathList
        val pathListField = ReflectUtil.getPrivateObjectByParent(classLoaderField, "pathList")
        pathListField ?: throw RuntimeException("Hotfit separateDexPathList pathList is no exist!")

        //获取elements
        val dexElementsField = ReflectUtil.getObject(pathListField, "dexElements")
        dexElementsField
            ?: throw RuntimeException("Hotfit separateDexPathList dexElements is no exist!")

        //获取补丁的elements
        val hotfitClassLoader = DexClassLoader(Configuration.HOTFIT_SOURCE, null, null, null)
        val hotfitPathListField =
            ReflectUtil.getPrivateObjectByParent(hotfitClassLoader, "pathList")
        hotfitPathListField
            ?: throw RuntimeException("Hotfit separateDexPathList pathList is no exist!")
        val hotfitDexElementsField = ReflectUtil.getObject(hotfitPathListField, "dexElements")
        hotfitDexElementsField
            ?: throw RuntimeException("Hotfit separateDexPathList dexElements is no exist!")

        //合并elements,自己的放前面
        val elementsClass = Class.forName("dalvik.system.DexPathList\$Element")
        val newMergedElements =
            ReflectUtil.mergeArray(elementsClass, hotfitDexElementsField, dexElementsField)

        //设置回去elements
        ReflectUtil.setObject(pathListField, "dexElements", newMergedElements)
    }

}
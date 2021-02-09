package com.nullpt.hotfit

import java.lang.reflect.InvocationTargetException

/**
 * @author BGQ
 * reflect util
 */
object ReflectUtil {

    /**
     * 反射获取目标对象属性值
     *
     * @param targetObj 目标对象
     * @param fieldName 属性名
     * @return 属性值
     */
    fun getObject(targetObj: Any, fieldName: String): Any? {
        val clazz: Class<*> = targetObj.javaClass
        return try {
            val field = clazz.getDeclaredField(fieldName)
            field.isAccessible = true
            field[targetObj]
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
            null
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 反射获取目标对象静态参数属性值
     *
     * @param className 目标类全限定名
     * @param fieldName 属性名
     * @return 属性值
     */
    fun getStaticObject(className: String, fieldName: String): Any? {
        return try {
            val clazz = Class.forName(className)
            val field = clazz.getDeclaredField(fieldName)
            field.isAccessible = true
            field[null]
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            null
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
            null
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 反射获取目标对象属性值
     *
     * @param targetObj 目标对象
     * @param fieldName 属性名
     * @return 属性值
     */
    fun getPublicObjectWithParent(targetObj: Any, fieldName: String): Any? {
        val clazz: Class<*> = targetObj.javaClass
        return try {
            val field = clazz.getField(fieldName)
            field.isAccessible = true
            field[targetObj]
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
            null
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 反射获取目标对象静态参数属性值
     *
     * @param className 目标类全限定名
     * @param fieldName 属性名
     * @return 属性值
     */
    fun getPublicStaticObjectWithPatent(className: String, fieldName: String): Any? {
        return try {
            val clazz = Class.forName(className)
            val field = clazz.getField(fieldName)
            field.isAccessible = true
            field[null]
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            null
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
            null
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 反射获取目标对象属性值
     *
     * @param targetObj 目标对象
     * @param fieldName 属性名
     * @return 属性值
     */
    fun getPrivateObjectByParent(targetObj: Any, fieldName: String): Any? {
        val clazz: Class<*> = targetObj.javaClass
        return try {
            val field = clazz.superclass.getDeclaredField(fieldName)
            field.isAccessible = true
            field[targetObj]
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
            null
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 反射获取目标对象静态参数属性值
     *
     * @param className 目标类全限定名
     * @param fieldName 属性名
     * @return 属性值
     */
    fun getPrivateStaticObjectByParent(className: String, fieldName: String): Any? {
        return try {
            val clazz = Class.forName(className).superclass
            val field = clazz.getDeclaredField(fieldName)
            field.isAccessible = true
            field[null]
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            null
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
            null
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 反射设置目标对象属性值
     *
     * @param targetObj 目标对象
     * @param fieldName 属性名
     * @param value     属性值
     */
    fun setObject(targetObj: Any, fieldName: String, value: Any?) {
        val clazz: Class<*> = targetObj.javaClass
        try {
            val field = clazz.getDeclaredField(fieldName)
            field.isAccessible = true
            field[targetObj] = value
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    /**
     * 反射设置目标对象静态参数属性值
     *
     * @param className 目标类全限定名
     * @param fieldName 属性名
     * @param value     属性值
     */
    fun setStaticObject(className: String, fieldName: String, value: Any?) {
        try {
            val clazz = Class.forName(className)
            val field = clazz.getDeclaredField(fieldName)
            field.isAccessible = true
            field[null] = value
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    /**
     * 反射调用目标对象方法
     *
     * @param targetObj      目标对象
     * @param methodName     方法名称
     * @param parameterTypes 方法参数类型
     * @param args           方法参数
     * @return 方法返回值
     */
    fun invokeMethod(
        targetObj: Any,
        methodName: String,
        parameterTypes: Array<Class<*>>,
        args: Array<Any?>
    ): Any? {
        val clazz: Class<*> = targetObj.javaClass
        return try {
            val method = clazz.getDeclaredMethod(methodName, *parameterTypes)
            method.invoke(targetObj, *args)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
            null
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            null
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 反射调用目标对象静态方法
     *
     * @param className      目标类全限定名
     * @param methodName     方法名称
     * @param parameterTypes 方法参数类型
     * @param args           方法参数
     * @return 方法返回值
     */
    fun invokeMethod(
        className: String,
        methodName: String,
        parameterTypes: Array<Class<*>>,
        args: Array<Any?>
    ): Any? {
        return try {
            val clazz = Class.forName(className)
            val method = clazz.getDeclaredMethod(methodName, *parameterTypes)
            method.invoke(null, *args)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
            null
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            null
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            null
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 反射合并数组对象
     *
     * @param arrA 数组A
     * @param arrB 数组B
     * @return 合并后的数组
     */
    fun mergeArray(componentType: Class<*>, arrA: Any, arrB: Any): Any {
        val lenA = java.lang.reflect.Array.getLength(arrA)
        val lenB = java.lang.reflect.Array.getLength(arrB)
        val arrRes = java.lang.reflect.Array.newInstance(componentType, lenA + lenB)
        for (i in 0 until lenA) {
            java.lang.reflect.Array.set(arrRes, i, java.lang.reflect.Array.get(arrA, i))
        }
        for (i in lenA until lenA + lenB) {
            java.lang.reflect.Array.set(arrRes, i, java.lang.reflect.Array.get(arrB, i - lenA))
        }
        return arrRes
    }
}
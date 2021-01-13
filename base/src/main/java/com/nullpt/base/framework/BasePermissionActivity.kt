package com.nullpt.base.framework

import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils


/**
 * @author BGQ
 * 权限申请基类
 */
open class BasePermissionActivity : BaseActivity() {


    /**
     * 申请相机权限
     */
    inline fun permission(
        @PermissionConstants.Permission permissions: Array<String>,
        crossinline onSuccess: () -> Unit = {},
        crossinline onRefuse: () -> Unit = {}
    ) {
        PermissionUtils.permission(*permissions)
            .callback(object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    onSuccess.invoke()
                }

                override fun onDenied() {
                    onRefuse.invoke()
                }

            }).request()
    }

    /**
     * 申请内存读写权限
     */
    inline fun permissionStorage(
        crossinline onSuccess: () -> Unit = {},
        crossinline onRefuse: () -> Unit = {}
    ) {
        permission(arrayOf(PermissionConstants.STORAGE), onSuccess, onRefuse)
    }

    /**
     * 申请相机权限
     */
    inline fun permissionCamera(
        crossinline onSuccess: () -> Unit = {},
        crossinline onRefuse: () -> Unit = {}
    ) {
        permission(arrayOf(PermissionConstants.CAMERA), onSuccess, onRefuse)
    }
}
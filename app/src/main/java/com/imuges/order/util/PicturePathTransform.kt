package com.imuges.order.util

import java.io.File

/**
 * @author BGQ
 * 照片临时图片路径替换保存
 */
object PicturePathTransform {

    fun transform(tempPath: String): String {
        val tempFile = File(tempPath)
        val targetDir = File("${tempFile.parent}/pic")
        if (!targetDir.exists()) {
            targetDir.mkdir()
        }
        val targetFile = File(targetDir, "pic_${System.currentTimeMillis()}.jpg")
        tempFile.renameTo(targetFile)
        return targetFile.absolutePath
    }

}
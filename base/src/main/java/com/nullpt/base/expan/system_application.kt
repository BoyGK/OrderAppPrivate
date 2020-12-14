package com.nullpt.base.expan

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileDescriptor
import java.io.IOException


/**
 * @author BGQ
 * 与系统应用相关的调度
 */

//防止和开发重复
private const val KEY = 0x1024

//打开相册
private const val INTENT_REQUEST_CODE_OPEN_GALLERY = KEY - 1

//打开相机 拍照
private const val INTENT_REQUEST_CODE_OPEN_CAMERA = KEY - 2

//打开相机 录视频
private const val INTENT_REQUEST_CODE_OPEN_CAMERA_VIDEO = KEY - 3

//打开文件选择
private const val INTENT_REQUEST_CODE_OPEN_FILE = KEY - 4

//拍照的临时图片路径
private lateinit var mTempPhotoPath: String

//拍照的临时Uri路径
private lateinit var mTempPhotoUri: Uri

//图片选择返回
private lateinit var mSelectImageCallUri: ((Uri: Uri) -> Unit)

//拍照返回
private lateinit var mTakePhotoCallUri: ((Uri: Uri) -> Unit)

//录视频返回
private lateinit var mRecordingCallUri: ((Uri: Uri) -> Unit)

//选择文件返回
private lateinit var mSelectFileCall: (name: String, fd: FileDescriptor) -> Unit

/**
 * 该方法一定要在BaseActivity中调用，不然该扩展内的所有方法都没有效果
 */
fun Activity.activityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (resultCode != RESULT_OK) {
        return
    }
    when (requestCode) {
        INTENT_REQUEST_CODE_OPEN_GALLERY -> {
            if (data == null) {
                return
            }
            if (data.data == null) {
                return
            }
            if (::mSelectImageCallUri.isInitialized) {
                mSelectImageCallUri(data.data!!)
            }
        }
        INTENT_REQUEST_CODE_OPEN_CAMERA -> {
            if (::mTakePhotoCallUri.isInitialized) {
                mTakePhotoCallUri(mTempPhotoUri)
            }
        }
        INTENT_REQUEST_CODE_OPEN_CAMERA_VIDEO -> {
            if (data == null) {
                return
            }
            if (data.data == null) {
                return
            }
            if (::mRecordingCallUri.isInitialized) {
                mRecordingCallUri(data.data!!)
            }
        }
        INTENT_REQUEST_CODE_OPEN_FILE -> {
            if (data == null) {
                return
            }
            if (data.data == null) {
                return
            }
            var fileName: String? = null
            val fileDescriptor: FileDescriptor?
            val contentResolver = applicationContext.contentResolver
            val cursor: Cursor? = contentResolver.query(
                data.data!!, null, null, null, null, null
            )
            cursor?.use {
                if (it.moveToFirst()) {
                    fileName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
            val parcelFileDescriptor = contentResolver.openFileDescriptor(data.data!!, "r")
            fileDescriptor = parcelFileDescriptor?.fileDescriptor
            if (fileName == null || fileDescriptor == null) {
                return
            }
            mSelectFileCall(fileName!!, fileDescriptor)
        }
        else -> {
        }
    }
}

/**
 * 选图片
 */
fun Activity.selectImage(uriCall: (uri: Uri) -> Unit) {
    mSelectImageCallUri = uriCall
    Intent(Intent.ACTION_GET_CONTENT).apply {
        type = "image/*"
    }.also { selectImageIntent ->
        selectImageIntent.resolveActivity(packageManager)?.also {
            startActivityForResult(selectImageIntent, INTENT_REQUEST_CODE_OPEN_GALLERY)
        }
    }
}


/**
 * 打开相机 拍照
 */
fun Activity.openCamera(uriCall: (uri: Uri) -> Unit) {
    mTakePhotoCallUri = uriCall
    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
        takePictureIntent.resolveActivity(packageManager)?.also {
            val photoFile: File = try {
                createImageFile(this)
            } catch (ex: IOException) {
                return
            }
            photoFile.also {
                mTempPhotoUri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", it)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mTempPhotoUri)
                startActivityForResult(takePictureIntent, INTENT_REQUEST_CODE_OPEN_CAMERA)
            }
        }
    }
}

@Throws(IOException::class)
private fun createImageFile(activity: Activity): File {
    val storageDir: File = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
    val file = File(storageDir, "tempImage.jpg")
    return if (file.exists()) {
        file.apply { mTempPhotoPath = absolutePath }
    } else {
        file.apply {
            createNewFile()
            mTempPhotoPath = absolutePath
        }
    }
}

/**
 * 打开相机 录视频
 */
fun Activity.openCameraForVideo(uriCall: (uri: Uri) -> Unit) {
    mRecordingCallUri = uriCall
    Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
        takeVideoIntent.resolveActivity(packageManager)?.also {
            startActivityForResult(takeVideoIntent, INTENT_REQUEST_CODE_OPEN_CAMERA_VIDEO)
        }
    }
}

/**
 * 选择文件
 */
fun Activity.openFile(
    mimeTypes: Array<String> = arrayOf("*/*"),
    fileCall: (name: String, fd: FileDescriptor) -> Unit
) {
    mSelectFileCall = fileCall
    Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "*/*"
        putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
    }.also { selectFileIntent ->
        selectFileIntent.resolveActivity(packageManager)?.also {
            startActivityForResult(selectFileIntent, INTENT_REQUEST_CODE_OPEN_FILE)
        }
    }
}
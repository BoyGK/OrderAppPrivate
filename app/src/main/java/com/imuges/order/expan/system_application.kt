package com.imuges.order.expan

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
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

private lateinit var currentPhotoPath: String

private lateinit var mSelectImageCallUri: ((Uri: Uri) -> Unit)

private lateinit var mTakePhotoCallUri: ((Uri: Uri) -> Unit)

private lateinit var mRecordingCallUri: ((Uri: Uri) -> Unit)

/**
 * 该方法一定要在BaseActivity中调用，不然该扩展内的所有方法都没有效果
 */
fun activityResult(requestCode: Int, resultCode: Int, data: Intent) {
    if (resultCode != RESULT_OK) {
        return
    }
    if (data.data == null) {
        return
    }
    when (requestCode) {
        INTENT_REQUEST_CODE_OPEN_GALLERY -> {
            if (::mSelectImageCallUri.isInitialized) {
                mSelectImageCallUri(data.data!!)
            }
        }
        INTENT_REQUEST_CODE_OPEN_CAMERA -> {
            if (::mTakePhotoCallUri.isInitialized) {
                mTakePhotoCallUri(data.data!!)
            }
        }
        INTENT_REQUEST_CODE_OPEN_CAMERA_VIDEO -> {
            if (::mRecordingCallUri.isInitialized) {
                mRecordingCallUri(data.data!!)
            }
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
    val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
        type = "image/*"
    }
    if (intent.resolveActivity(packageManager) != null) {
        startActivityForResult(intent, INTENT_REQUEST_CODE_OPEN_GALLERY)
    }
}


/**
 * 打开相机 拍照
 * @test
 */
fun Activity.openCamera(uriCall: (uri: Uri) -> Unit) {
    mTakePhotoCallUri = uriCall
    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
        takePictureIntent.resolveActivity(packageManager)?.also {
            val photoFile: File? = try {
                createImageFile(this)
            } catch (ex: IOException) {
                return
            }
            photoFile?.also {
                val photoURI: Uri =
                    FileProvider.getUriForFile(this, "com.imuges.order.fileprovider", it)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
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
        file.apply { currentPhotoPath = absolutePath }
    } else {
        File.createTempFile("tempImage", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }
}

/**
 * 打开相机 录视频
 * @test
 */
fun Activity.openCameraForVideo(uriCall: (uri: Uri) -> Unit) {
    mRecordingCallUri = uriCall
    val intent = Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA)
    if (intent.resolveActivity(packageManager) != null) {
        startActivityForResult(intent, INTENT_REQUEST_CODE_OPEN_CAMERA_VIDEO)
    }
}
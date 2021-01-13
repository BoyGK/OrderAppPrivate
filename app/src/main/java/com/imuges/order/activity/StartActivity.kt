package com.imuges.order.activity

import android.annotation.SuppressLint
import com.imuges.order.R
import com.imuges.order.base.BaseFullTitleActivity
import com.nullpt.base.framework.BaseMVPActivity
import com.nullpt.base.framework.BasePresenter
import com.nullpt.base.framework.ContentView
import kotlinx.android.synthetic.main.activity_start.*

@SuppressLint("NonConstantResourceId")
@ContentView(R.layout.activity_start)
class StartActivity : BaseFullTitleActivity() {

    override fun getPresenterMap(): Map<String, BasePresenter<out BaseMVPActivity>> {
        return mapOf()
    }

    override fun onCreate() {
        val color = getColor(R.color.mainTheme)
        setStateBarColor(color)
        backCard.mBackColor = color

        loadCard3.setAnimationFinishCallBack {
            permissionStorage(onSuccess = {
                MainActivity.startActivity(this)
                finish()
            }, onRefuse = {
                finish()
            })
        }
    }

}
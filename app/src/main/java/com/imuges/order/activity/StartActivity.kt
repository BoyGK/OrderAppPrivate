package com.imuges.order.activity

import android.annotation.SuppressLint
import com.imuges.order.R
import com.imuges.order.base.BaseFullTitleActivity
import com.imuges.order.base.BaseMVPActivity
import com.imuges.order.base.BasePresenter
import com.imuges.order.base.ContentView
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
            MainActivity.startActivity(this)
            finish()
        }
    }

}
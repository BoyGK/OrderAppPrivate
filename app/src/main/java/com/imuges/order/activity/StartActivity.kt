package com.imuges.order.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Handler
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
        val color = Color.parseColor("#FF8282CF")
        setStateBarColor(color)
        backCard.mBackColor = color

        Handler().postDelayed({
            MainActivity.startActivity(this)
            finish()
        }, 1000L)
    }

}
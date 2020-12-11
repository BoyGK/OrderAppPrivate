package com.imuges.statistics.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.imuges.statistics.R
import kotlinx.android.synthetic.main.activity_statistics_main.*

/**
 * @author BGQ
 * 统计模块
 */
class StatisticsMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics_main)

        test.text = "123456"
    }
}
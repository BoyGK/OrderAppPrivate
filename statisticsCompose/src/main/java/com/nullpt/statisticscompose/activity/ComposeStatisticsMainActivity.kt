package com.nullpt.statisticscompose.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.ViewModelProvider
import com.nullpt.statisticscompose.activity.base.ComposeBaseActivity
import com.nullpt.statisticscompose.activity.ui.theme.OrderAppTheme
import com.nullpt.statisticscompose.viewmodel.StatisticsMainViewModel

/**
 * @author BGQ
 * 统计模块
 * 使用Compose方式开发
 */
class ComposeStatisticsMainActivity : ComposeBaseActivity() {

    /**
     * 数据对象
     */
    private val viewModel by lazy { ViewModelProvider(this).get(StatisticsMainViewModel::class.java) }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, ComposeStatisticsMainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrderAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ActivityContent(viewModel)
                }
            }
        }
    }

    @Composable
    fun ActivityContent(viewModel: StatisticsMainViewModel) {
        Column {
            TitleBar("统计")
        }
    }
}
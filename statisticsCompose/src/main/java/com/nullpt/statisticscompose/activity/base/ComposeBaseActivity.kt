package com.nullpt.statisticscompose.activity.base

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.nullpt.statisticscompose.R

/**
 * @author BGQ
 * 通用Compose组件
 */
open class ComposeBaseActivity : AppCompatActivity() {

    /**
     * TitleBar
     */
    @Composable
    fun TitleBar(
        text: String,
        backIcon: ImageVector = vectorResource(id = R.drawable.ic_arrow_back_white)
    ) {
        TopAppBar {
            Row(modifier = Modifier.fillMaxSize()) {
                Icon(
                    imageVector = backIcon,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 16.dp)
                )
                Text(
                    text = text,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp),
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}
package com.example.im_test.Extra

import android.content.Intent
import android.support.v7.widget.Toolbar
import com.example.im_test.R
import com.example.im_test.View.Activity.SettingActivity

interface ToolbarManager {

    val toolbar: Toolbar

    fun initMainToolbar(){

        toolbar.title = "教学辅助系统"
        toolbar.inflateMenu(R.menu.user)
        toolbar.setOnMenuItemClickListener {
            toolbar.context.startActivity(Intent(toolbar.context, SettingActivity::class.java))
            true
        }
    }

    fun setToolbarTitle(title: String){

        toolbar.title = title
    }
}
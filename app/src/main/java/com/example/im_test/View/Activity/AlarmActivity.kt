package com.example.im_test.View.Activity

import android.app.Service
import com.example.im_test.Base.BaseActivity
import com.example.im_test.R
import kotlinx.android.synthetic.main.activity_alarm.*
import org.jetbrains.anko.startActivity

class AlarmActivity:BaseActivity() {

    override fun setLayout(): Int = R.layout.activity_alarm

    override fun init() {
        super.init()
        //初始化加入操作
        initAddAlarm()
    }

    private fun initAddAlarm() {
        val alarmManager = getSystemService(Service.ALARM_SERVICE)
    }

    override fun initListener() {
        back.setOnClickListener {
            startActivity<MainActivity>()
            this.finish()
        }

        add.setOnClickListener {
            startActivity<TimeChooseActivity>()
        }
    }


}
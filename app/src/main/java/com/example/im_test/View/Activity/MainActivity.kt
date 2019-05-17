package com.example.im_test.View.Activity

import android.support.v7.widget.Toolbar
import com.example.im_test.Base.BaseActivity
import com.example.im_test.Extra.ToolbarManager
import com.example.im_test.R
import com.example.im_test.View.Fragment.AlarmFragment
import com.example.im_test.View.Fragment.HomeFragment
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import uk.co.markormesher.android_fab.FloatingActionButton

class MainActivity : BaseActivity(),ToolbarManager {

//重载toolbar
    override val toolbar:Toolbar by lazy {
        find<Toolbar>(R.id.toolbar)
    }

//两个fragment
    private val home by lazy {
        HomeFragment()
    }

    private val alarm by lazy {
        AlarmFragment()
    }

//返回xml文件层，toolbar包括两层，一层view，一层modle；；在xml中写了view
    override fun setLayout(): Int = R.layout.activity_main


//在初始化中，加载toolbar的设置、点击事件
    override fun init() {
        super.init()
        initMainToolbar()
    }

    override fun initListener() {
        super.initListener()

        val fab = findViewById<FloatingActionButton>(R.id.fab_main)
        fab.setOnClickListener {
            toast("日程")
            startActivity<TimeChooseActivity>()
        }

    }


}




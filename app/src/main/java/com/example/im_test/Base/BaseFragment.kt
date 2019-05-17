package com.example.im_test.Base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(setLayout(),null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {  //在第一步初始化过后再进行的第二次初始化
        init()
        initListener()
    }

    open fun initListener() {

    }

    abstract fun setLayout(): Int   //返回一个xml

    open fun init(){    //公共方法

    }
}


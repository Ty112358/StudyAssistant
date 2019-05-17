package com.example.im_test.Contract

import com.example.im_test.Base.BaseContract

interface SplashContract {

    interface checkPresenter: BaseContract {

        fun checkLogin()
    } //接口1 --> 再presenter层


    interface viewPresenter{

        fun isOnLogin()//在线

        fun notOnLogin()//不在线
    } //接口2 --> 在View层，完成相应的事情
}
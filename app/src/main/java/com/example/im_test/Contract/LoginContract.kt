package com.example.im_test.Contract

import com.example.im_test.Base.BaseContract

interface LoginContract:BaseContract {

    interface Presenter{

        fun login(name:String,password:String)
    }


    interface View {

        //用户名和密码校验
        fun userNameError()
        fun userPasswordError()
        //正在登陆
        fun StartLogin()
        //登录成功、登录失败
        fun LoginSUccess()
        fun LoginFaild()

    }

}
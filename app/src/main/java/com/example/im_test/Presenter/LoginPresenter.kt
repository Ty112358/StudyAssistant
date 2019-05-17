package com.example.im_test.Presenter

import android.os.Handler
import android.os.Looper
import com.example.im_test.Adapter.EMcallbackAdapter
import com.example.im_test.Contract.LoginContract
import com.hyphenate.chat.EMClient

class LoginPresenter(val view: LoginContract.View):LoginContract.Presenter { //继承presenter，输入view


    override fun login(name: String, password: String) {  //登录逻辑 1、用户名密码是否规范 2、若错误，返回错误信息（view层），正确的话登录服务器

        if (name.matches(Regex("^[0-9]{5,11}"))){
            if (password.matches(Regex("^[A-Za-z0-9]{6,26}"))){

                view.StartLogin()

                LoginServer(name,password)

                }else view.userPasswordError()

        }else view.userNameError()
    }
//主线程
    companion object {
        val handler by lazy {
            Handler(Looper.getMainLooper())
        }
    }

    private fun uiThread(f:() -> Unit){
        handler.post { f() }
    }

    lateinit var userID : String


    private fun LoginServer(name: String,password: String) {

        EMClient.getInstance().login(name,password,object : EMcallbackAdapter(){

            override fun onSuccess() {
                EMClient.getInstance().chatManager().loadAllConversations() //加载所有会话
                EMClient.getInstance().groupManager().loadAllGroups() //加载所有群组
                EMClient.getInstance().groupManager().getJoinedGroupsFromServer()
                //在主线程通知view
                uiThread { view.LoginSUccess() }

            }

            override fun onError(p0: Int, p1: String?) {
                uiThread { view.LoginFaild() }
            }
        })



        }






    }



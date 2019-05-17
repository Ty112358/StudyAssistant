package com.example.im_test.Presenter

import com.example.im_test.Contract.SplashContract
import com.hyphenate.chat.EMClient


class SplashPresenter(val view:SplashContract.viewPresenter):SplashContract.checkPresenter {


    override fun checkLogin() {
        if (isLogin()) return view.isOnLogin() else return view.notOnLogin()
    }

    private fun isLogin(): Boolean =
         EMClient.getInstance().isConnected && EMClient.getInstance().isLoggedInBefore

}
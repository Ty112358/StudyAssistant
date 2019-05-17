package com.example.im_test.View.Activity

import android.os.Handler
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.view.View
import com.example.im_test.Base.BaseActivity
import com.example.im_test.Contract.SplashContract
import com.example.im_test.Presenter.SplashPresenter
import com.example.im_test.R
import kotlinx.android.synthetic.main.activity_splash.*
//import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity

class SplashActivity: BaseActivity(),SplashContract.viewPresenter,ViewPropertyAnimatorListener {


    val handler by lazy {
        Handler()
    }  //用handler处理延时，使用懒加载

    val presenter = SplashPresenter(this)
    //new presenter


    override fun setLayout(): Int =
        R.layout.activity_splash


    override fun init() {
        ViewCompat.animate(splash1).scaleY(1.0f).scaleX(1.0f).setListener(this).setDuration(2000)
    }

    override fun onAnimationEnd(p0: View?) {
        presenter.checkLogin()
    }


    override fun isOnLogin() {  //如果已经登录,直接跳转到Main页面
        startActivity<MainActivity>()
        finish()
    }

    override fun notOnLogin() {
        handler.postDelayed({     //用lambda表达式
            startActivity<LoginActivity>()
            finish()
        },0)


    }


    //这两个不用写
    override fun onAnimationCancel(p0: View?) {}

    override fun onAnimationStart(p0: View?) {}





   /** override fun init() {
        ViewCompat.animate(splash1).scaleY(1.0f).scaleX(1.0f).setListener(this).setDuration(2000)
    }


    //只是为了导入this，没什么用
    override fun onAnimationEnd(p0: View?) {
        startActivity<MainActivity>()
        finish()
    }
    override fun onAnimationCancel(p0: View?) {}
    override fun onAnimationStart(p0: View?) {}
   **/
}
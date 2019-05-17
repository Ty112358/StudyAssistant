package com.example.im_test.View.Activity

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.example.im_test.Base.BaseActivity
import com.example.im_test.Contract.LoginContract
import com.example.im_test.Presenter.LoginPresenter
import com.example.im_test.R
import kotlinx.android.synthetic.main.login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity: BaseActivity(),LoginContract.View {


    override fun setLayout(): Int = R.layout.login

//初始化
    override fun init() {
        super.init()
        userLogin.setOnClickListener{Login()} //绑定监听事件->登录按钮
        passWord.setOnEditorActionListener { v, actionId, event ->
            /* 密码 输入完后按enter */
            Login()
            true
        }
        onBackPressed() //屏蔽返回键
    }

//登录
    private fun Login() {  //登录操作

        //隐藏软键盘
        hideSoftKeyBoard()
        if (hasWrittenPermission()){
            val presenter = LoginPresenter(this)
            val name = userName.text.trim().toString()
            val password = passWord.text.trim().toString()
            presenter.login(name,password)

        }else applyWrittenPermission()

    }

//登录检查
    override fun userNameError() {
        userName.error = "账号不合法，必须是10位数字"
    }

    override fun userPasswordError() {
        passWord.error = "密码必须是英文字母与数字的组合（区分大小写）"
    }

//登录状态
    override fun StartLogin() {
        showProgress("正在登录")
    }

    override fun LoginSUccess() {

        //隐藏进度条
        dissmissProgress()
        //打开主界面
        startActivity<MainActivity>()
        //退出登录界面
        finish()
    }

    override fun LoginFaild() {
        dissmissProgress()
        toast("登录失败")
    }

    override fun onBackPressed() {
        //屏蔽返回键
    }

//写入权限判断
    private fun hasWrittenPermission():Boolean{
      return ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
}
//权限申请
    fun applyWrittenPermission(){
        val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this, permissions, 0)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Login()
        }else toast("权限被拒绝")
    }

}
package com.example.im_test.View.Activity

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.widget.Toolbar
import com.alibaba.fastjson.JSON
import com.example.im_test.Adapter.EMcallbackAdapter
import com.example.im_test.Base.BaseActivity
import com.example.im_test.Extra.ToolbarManager
import com.example.im_test.R
import com.example.im_test.data.HomeworkList
import com.hyphenate.chat.EMClient
import com.vincent.filepicker.Constant
import com.vincent.filepicker.activity.NormalFilePickActivity
import com.vincent.filepicker.filter.entity.NormalFile
import kotlinx.android.synthetic.main.activity_setting.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.*


class SettingActivity:BaseActivity() ,ToolbarManager{

    override val toolbar: Toolbar by lazy { find<Toolbar>(R.id.toolbar) } //懒加载
    //懒加载，学生ID
    val studentID by lazy {
        EMClient.getInstance().currentUser
    }

    var homeworkName : String = "hello"

//返回一个设置界面，设置页面没有直接写，而使用了引入的方式；
    override fun setLayout(): Int = R.layout.activity_setting

    override fun init() {
        setToolbarTitle("个人中心")
    }

    private fun initResult(function: () -> Unit) {

    }

    override fun initListener() {
        //退出登录监听
        re.setOnClickListener {
            logout()
        }
        homework.setOnClickListener {
            uploadHomework()
        }
        test.setOnClickListener {
            testFun()
        }
    }

    private fun testFun() {
        val intent = Intent(this,NormalFilePickActivity::class.java)
        intent.putExtra(Constant.MAX_NUMBER,9)
        intent.putExtra(NormalFilePickActivity.SUFFIX, arrayOf("xls","doc","pdf","ppt","pptx","xlsx","txt"))
        startActivityForResult(intent,Constant.REQUEST_CODE_PICK_FILE)

    }


    //logout方法
    private fun logout() {
        EMClient.getInstance().logout(true, object : EMcallbackAdapter() {

            override fun onSuccess() {
                runOnUiThread { //在主线程中执行
                    startActivity<LoginActivity>()
                    EMClient.getInstance().logout(true)
                    toast("退出登录成功")
                }

            }

            override fun onError(code: Int, message: String?) {
                runOnUiThread {
                    toast("退出失败")
                }
            }
        })

    }

    //提交作业
    private fun uploadHomework() {
        //toast("homework")
        doAsync {
              val okHttpGet = OkHttpClient()
              val request = Request.Builder()
                  .get()
                  .url("http://47.107.105.126:5001/homework?id=$studentID")
                  .build()
              val response = okHttpGet.newCall(request).execute()
              if (response.isSuccessful) {
                  //获得响应实体
                  val json = response.body()!!.string()
                  val jsonList = JsonToArray(json)
                  //判断是否为空
                  if (jsonList != null) {
                      uiThread {
                          toast("get")
                          val dialog = AlertDialog.Builder(it)
                          dialog.setTitle("待提交科目")
                          dialog.setSingleChoiceItems(jsonList,0,DialogInterface.OnClickListener { dialog, which ->
                              doAsync {
                                  homeworkName = jsonList[which]
                              }
                          })
                          //点击确认按钮后
                          dialog.setPositiveButton("确定", DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->
                              //toast(homeworkName)
                              /*val filePicker = LFilePicker()
                                  filePicker.withActivity(it)
                                  filePicker.withRequestCode(1000)
                                  filePicker.start()*/
                              val intent = Intent(it,NormalFilePickActivity::class.java)
                              intent.putExtra(Constant.MAX_NUMBER,9)
                              intent.putExtra(NormalFilePickActivity.SUFFIX, arrayOf("xls","doc","pdf","ppt","pptx","xlsx","txt"))
                              startActivityForResult(intent,Constant.REQUEST_CODE_PICK_FILE)

                          })
                          dialog.setNegativeButton("取消",DialogInterface.OnClickListener { dialog, which ->
                              null
                          })
                          dialog.show()
                      }
                  }else{
                      toast("无可提交作业")
                  }

              } else {
                  //throw IOException("Unexpected code $response")
                  uiThread {
                      toast("获取失败")
                  }
              }

          }
    }

    fun JsonToArray(json:String): kotlin.Array<String> {
        val homeworkClass = HomeworkList::class
        val jsonArray = JSON.parseArray(json, homeworkClass.javaObjectType)
        /*var classArray = arrayListOf<String>()
        jsonArray.forEach {
            classArray.add(it.classname)
        }*/
        val len = jsonArray.size
        println(jsonArray.toString())
        var list=Array(len,{""})
        for (i in 0..len-1){
            list[i] = jsonArray[i].classname+'（'+jsonArray[i].intro+'）'
        }
        return list
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(resultCode){
            Constant.REQUEST_CODE_PICK_FILE -> {
                if (requestCode == Activity.RESULT_OK && data != null){
                    val list = data.getParcelableArrayListExtra<NormalFile>(Constant.RESULT_PICK_FILE)
                    toast("get")

                }
            }
            else -> {
                println("test")
                toast("未选择文件")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}
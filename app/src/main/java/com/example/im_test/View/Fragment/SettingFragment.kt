package com.example.im_test.View.Fragment

import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceScreen
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.im_test.R
import org.jetbrains.anko.toast

class SettingFragment:PreferenceFragment() {



//返回的个人中心页面
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        addPreferencesFromResource(R.xml.setting)
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    //设置key的监听
    override fun onPreferenceTreeClick(preferenceScreen: PreferenceScreen?, preference: Preference?): Boolean {
        val key = preference?.key
        //关于 按键
        if("about" == key){
            toast("haha")
        }

        //提交作业 按键
        if ("homework" == key){
            toast("homework")
          /* doAsync {
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
                            val dialog = AlertDialog.Builder(context)
                                .setTitle("请选择科目")
                                .setPositiveButton("确定",DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->
                                    null
                                })
                                .setNegativeButton("取消",DialogInterface.OnClickListener { dialog, which ->
                                    null
                                })
                                .show()

                        }
                    }else{
                        toast("无可提交作业")
                    }


                } else {
                    throw IOException("Unexpected code $response")
                }

            } */

        }
        return super.onPreferenceTreeClick(preferenceScreen, preference)
    }




}
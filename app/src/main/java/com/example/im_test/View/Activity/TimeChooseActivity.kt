package com.example.im_test.View.Activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import cn.aigestudio.datepicker.bizs.calendars.DPCManager
import cn.aigestudio.datepicker.cons.DPMode
import com.example.im_test.Adapter.EventListAdapter
import com.example.im_test.Base.BaseActivity
import com.example.im_test.R
import com.example.im_test.data.AlarmEvent
import kotlinx.android.synthetic.main.time_choose.*
import kotlinx.android.synthetic.main.time_choose.view.*
import org.jetbrains.anko.toast
import java.util.*

class TimeChooseActivity:BaseActivity() {

    companion object{ //获取初始化时间
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1

        val day =cal.get(Calendar.DAY_OF_MONTH)
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val min = cal.get(Calendar.MINUTE)
    }

    val dpc by lazy { //每一个条目管理
        DPCManager.getInstance()
    }
    val tmpTR = mutableListOf<String>() //右上角标志列表
    val tmp = mutableListOf<String>() //背景列表

    val eventList = mutableListOf<AlarmEvent>()

    override fun setLayout(): Int {
        return R.layout.time_choose
    }

    override fun init() {
        initDeafaultTime()
        initdefault()
        //下拉刷新框设置
        swipe_event.apply {
            isRefreshing = true
            setColorSchemeResources(R.color.material_deep_teal_500)
            recycler_event.adapter?.notifyDataSetChanged()
            isRefreshing = false
        }
        //item事件
        recycler_event.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?
            adapter = EventListAdapter(context,eventList)
        }
      //  initdefaultBG()

    }



    private fun initdefault() {
        datepicker.apply {
            setDate(year, month)
            setMode(DPMode.SINGLE)

        }
    }

    private fun initDeafaultTime() {
        val defaulttime = year.toString()+"-"+month.toString()+"-"+day.toString()
        text_time.text = defaulttime
    }

    override fun initListener() {
        //日历点击事件
        datepicker.setOnDatePickedListener(cn.aigestudio.datepicker.views.DatePicker.OnDatePickedListener{date ->
            toast(date)
            text_time.text = date
        })

        //输入事件，弹出dialog框
        fab_add.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val et = EditText(this)
            //val view = LayoutInflater.from(this).inflate(R.layout.add_envent,null)
            dialog.setTitle("请输入事件")
            dialog.setView(et)
            //dialog确定
            dialog.setPositiveButton("确定",DialogInterface.OnClickListener { p0, p1 ->
                var eventCurrent = "无描述"
                if (et.text != null){
                    eventCurrent = et.text.toString()
                }else{
                    toast(eventCurrent)
                    null
                }
                val dateCurrent = text_time.text.trim().toString()
                val eventItem = AlarmEvent(dateCurrent,eventCurrent)
                eventAdd(eventItem)
            })
            //dialog 取消
            dialog.setNegativeButton("取消",DialogInterface.OnClickListener{dialog, which ->
                null
            })
          /*  text_thing.setOnEditorActionListener { p0, p1, p2 ->
                var eventCurrent = "无描述"
                if (text_thing != null){
                    eventCurrent = text_thing.text.toString()
                }else{
                    toast("无描述")
                    null
                }
                val dateCurrent = text_time.text.trim().toString()
                val eventItem = AlarmEvent(dateCurrent,eventCurrent)
                eventAdd(eventItem)
                true
            }*/
            dialog.show()
        }

    }


//日期背景色
    fun tmpAdd(pi:String){

        if (tmp.contains(pi)){
            null
        }else{
            tmp.add(pi)
            dpc.setDecorBG(tmp)
        }

    }
//日期 右上角小点
    fun tmpTRAdd(pi:String){

        if (tmpTR.contains(pi)){
            null
        }else{
            tmpTR.add(pi)
            dpc.setDecorTR(tmpTR)
        }


    }

    //加入事件条目

    fun eventAdd(currentEvent:AlarmEvent){
        if (eventList.contains(currentEvent)){
            null
        }else{
            eventList.add(currentEvent)
            tmpTRAdd(currentEvent.date)
            tmpAdd(currentEvent.date)
            recycler_event.adapter?.notifyDataSetChanged()
        }
    }

   /* private fun initdefaultBG() {

    }
    */
}

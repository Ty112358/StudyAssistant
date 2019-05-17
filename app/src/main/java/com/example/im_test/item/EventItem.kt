package com.example.im_test.item

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.im_test.R
import com.example.im_test.data.AlarmEvent
import kotlinx.android.synthetic.main.event_item.view.*

class EventItem (context: Context?, attrs: AttributeSet?=null) : RelativeLayout(context, attrs) {


    init {
        View.inflate(context, R.layout.event_item,this)
    }


    //绑定时间，设定每一个group的名字、首字母
    fun bindView(eventItem: AlarmEvent) {
        text_date.text = eventItem.date
        text_event.text = eventItem.thing
    }


}
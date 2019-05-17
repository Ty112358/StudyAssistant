package com.example.im_test.item

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.im_test.R
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.util.DateUtils
import kotlinx.android.synthetic.main.msg_recevie_item.view.*
import java.util.*

class RecevieMsgItem(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {


    init {
            View.inflate(context, R.layout.msg_recevie_item,this)
    }

    fun bindView(msg: EMMessage) {
        updateTime(msg)
        updateUserName(msg)
        updateMsg(msg)
        updateProgress(msg)
    }

    private fun updateProgress(msg: EMMessage) {
        msg.status().let {
            when(it){
                EMMessage.Status.INPROGRESS ->{

                }

                EMMessage.Status.SUCCESS ->{

                }

                EMMessage.Status.FAIL -> {

                }
            }
        }
    }

    private fun updateMsg(msg: EMMessage) {
        receiveMessage.text = (msg.body as EMTextMessageBody).message
    }

    private fun updateUserName(msg: EMMessage) {
        user_name.text = msg.from
    }

    private fun updateTime(msg: EMMessage) {
        time_stamp.text = DateUtils.getTimestampString(Date(msg.msgTime))
    }


}
package com.example.im_test.Contract

import com.example.im_test.Base.BaseContract
import com.hyphenate.chat.EMMessage

interface ChattingContract {

    interface Presenter:BaseContract{
        fun sendMsg(userName:String,message : String)
        fun addMsg(groupname: String, msgs: MutableList<EMMessage>?)
        fun loadMsgs(groupname: String)
    }

    interface View{

        fun onStartSendMsg()
        fun onSendMsgSuccess()
        fun onSendMsgFailed()
        fun onLoadMsgs()
        fun onMoreMsgsLoad(size: Int)


    }
}
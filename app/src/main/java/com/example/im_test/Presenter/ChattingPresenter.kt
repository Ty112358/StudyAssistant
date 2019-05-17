package com.example.im_test.Presenter

import android.os.Handler
import android.os.Looper
import com.example.im_test.Adapter.EMcallbackAdapter
import com.example.im_test.Contract.ChattingContract
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import org.jetbrains.anko.doAsync


class ChattingPresenter(val view:ChattingContract.View):ChattingContract.Presenter {

//主线程调用
    companion object {
        val handler by lazy {
            Handler(Looper.getMainLooper())
        }
    }

    private fun uiThread(f:() -> Unit){
        LoginPresenter.handler.post { f() }
    }

    //创建一个可改列表，管理消息
    val Msgs = mutableListOf<EMMessage>()


    //发送消息
    override fun sendMsg(groupName: String, message: String) {
        //创建一条文本消息
        val emMsg = EMMessage.createTxtSendMessage(message, groupName)

        emMsg.setMessageStatusCallback(object : EMcallbackAdapter() {

            override fun onSuccess() {
                uiThread { view.onSendMsgSuccess() }
            }

            override fun onError(p0: Int, p1: String?) {
                uiThread { view.onSendMsgFailed() }
            }

            override fun onProgress(p0: Int, p1: String?) {
                EMClient.getInstance().groupManager().joinedGroupsFromServer
            }

        })

        Msgs.add(emMsg) //加入列表
        view.onStartSendMsg() //开始
        EMClient.getInstance().chatManager().sendMessage(emMsg) //发送
    }

    override fun addMsg(groupname: String, msgs: MutableList<EMMessage>?) {
        msgs?.let { Msgs.addAll(it) } //加入列表
        //提交
        val conversation = EMClient.getInstance().chatManager().getConversation(groupname)
        conversation.markAllMessagesAsRead()
    }

    override fun loadMsgs(groupname: String) {
        doAsync {
            val conversation = EMClient.getInstance().chatManager().getConversation(groupname)
            Msgs.addAll(conversation.allMessages)
        }
        uiThread {
            view.onLoadMsgs()
        }
    }

    fun loadMoreMsgs(groupname: String) {
        doAsync {
            val conversation = EMClient.getInstance().chatManager().getConversation(groupname)
            val startMsgId = Msgs[0].msgId
            val loadMoreMsgDB = conversation.loadMoreMsgFromDB(startMsgId,15)
            Msgs.addAll(0,loadMoreMsgDB)
            uiThread {
                view.onMoreMsgsLoad(loadMoreMsgDB.size)
            }
        }

    }

}
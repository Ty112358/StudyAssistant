package com.example.im_test.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.im_test.item.RecevieMsgItem
import com.example.im_test.item.SendMsgItem
import com.hyphenate.chat.EMMessage

class MessageListAdapter(val context : Context,val Msgs :List<EMMessage>) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object {
        val TYPE_SEND_MSG =1
        val TYPE_RECEIVE_MSG = 0
    }

    override fun getItemViewType(position: Int): Int {

        if (Msgs[position] == EMMessage.Direct.SEND){
            return TYPE_SEND_MSG
        }
        else{
            return TYPE_RECEIVE_MSG
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == TYPE_SEND_MSG){
            return SendMsgViewHolder(SendMsgItem(context))
        }else return ReceiveMsgViewHolder(RecevieMsgItem(context))

    }

    override fun getItemCount(): Int = Msgs.size

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int){
        val ci = p0.itemView as RecevieMsgItem
        ci.bindView(Msgs[p1])
    }


    //分别创建send和receive的viewholder
    class SendMsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class ReceiveMsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
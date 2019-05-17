package com.example.im_test.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.im_test.View.Activity.ChattingActivity
import com.example.im_test.data.GroupListItem
import com.example.im_test.item.GroupItem
import org.jetbrains.anko.startActivity

class RecyclerAdapter(val context: Context,val groupList :MutableList<GroupListItem>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        return RecyclerItemViewHolder(GroupItem(context))
    }

    override fun getItemCount(): Int = groupList.size

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {

       val gi = p0.itemView as GroupItem
        gi.bindView(groupList[p1])

        val groupName = groupList[p1].groupName
        gi.setOnClickListener { context.startActivity<ChattingActivity>("groupname" to groupName) }
    }

    class RecyclerItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
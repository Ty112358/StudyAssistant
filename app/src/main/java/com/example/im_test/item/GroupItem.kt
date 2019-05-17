package com.example.im_test.item

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.im_test.R
import com.example.im_test.data.GroupListItem
import kotlinx.android.synthetic.main.item_view.view.*

class GroupItem(context: Context?, attrs: AttributeSet?=null) : RelativeLayout(context, attrs) {


    init {
        View.inflate(context, R.layout.item_view,this)
    }


//绑定时间，设定每一个group的名字、首字母
    fun bindView(groupListItem: GroupListItem) {
    group_name.text = groupListItem.groupName
    group_first.text = groupListItem.firstText
    }


}
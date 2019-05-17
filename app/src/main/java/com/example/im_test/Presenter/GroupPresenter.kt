package com.example.im_test.Presenter

import com.example.im_test.Contract.GroupContract
import com.example.im_test.data.GroupListItem
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

//判断加载是否成功，另外获取群组信息
class GroupPresenter(val view : GroupContract.View):GroupContract.Presenter {

    val groupList = mutableListOf<GroupListItem>()//用这个表来存放界面所需要的群组成员信息

    override fun LoadContacts() {
        doAsync {
            val group = EMClient.getInstance().groupManager().joinedGroupsFromServer
            try {
                group.forEach {
                    //val listItem = GroupListItem(it.groupName,it.groupName[0].toUpperCase()) //取群名称和首字母

                    val listItem = GroupListItem(it.groupName,"课程代码: "+it.groupId)

                    //判断课程是否已经在数组中,若不在，插入list中
                    if (groupList.contains(listItem)){
                        null
                    }else{
                        groupList.add(listItem)

                    }
                }

                uiThread {
                    view.onLoadSuccessed()

                }
            }
            catch (e: HyphenateException){
                uiThread {
                    view.onLoadFaild()

                }
            }
        }
    }
}
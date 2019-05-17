package com.example.im_test.Contract

import com.example.im_test.Base.BaseContract

interface GroupContract {

    interface Presenter:BaseContract{
        //读取联系人
        fun LoadContacts()
    }

    interface View{
        //读取成功、失败
        fun onLoadSuccessed()
        fun onLoadFaild()
    }
}
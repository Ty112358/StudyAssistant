package com.example.im_test.View.Fragment

import android.support.v7.widget.LinearLayoutManager
import com.example.im_test.Adapter.RecyclerAdapter
import com.example.im_test.Base.BaseFragment
import com.example.im_test.Contract.GroupContract
import com.example.im_test.Presenter.GroupPresenter
import com.example.im_test.R
import kotlinx.android.synthetic.main.home.*
import org.jetbrains.anko.support.v4.toast

class HomeFragment:BaseFragment(),GroupContract.View {


    val presenter = GroupPresenter(this)

    override fun setLayout(): Int {
        return R.layout.home
    }

    override fun init() {
        super.init()
        swipe.apply {
            //刷新
            isRefreshing = true
            setColorSchemeResources(R.color.material_deep_teal_500)
            setOnRefreshListener {
                presenter.LoadContacts()
                toast("加载成功")
            } //下拉监听
        }

        recycler.apply {
            //对循环列表进行适配
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = RecyclerAdapter(context,presenter.groupList)
        }

        presenter.LoadContacts()
    }

//加载成功与加载失败
    override fun onLoadSuccessed() {
        swipe.isRefreshing = false
        recycler.adapter?.notifyDataSetChanged()
    }

    override fun onLoadFaild() {
        swipe.isRefreshing = false
        toast("加载失败")
    }

}
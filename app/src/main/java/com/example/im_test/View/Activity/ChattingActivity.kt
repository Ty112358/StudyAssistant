package com.example.im_test.View.Activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.im_test.Adapter.MessageListAdapter
import com.example.im_test.Adapter.MsgListenerAdapter
import com.example.im_test.Base.BaseActivity
import com.example.im_test.Contract.ChattingContract
import com.example.im_test.Presenter.ChattingPresenter
import com.example.im_test.R
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import kotlinx.android.synthetic.main.chat_activity.*
import kotlinx.android.synthetic.main.header.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.io.IOException
import okhttp3.Callback as Okhttp3Callback

class ChattingActivity:BaseActivity(),ChattingContract.View {


    override fun setLayout(): Int = R.layout.chat_activity

    val presenter = ChattingPresenter(this)
    lateinit var groupname :String
    //懒加载 当前用户的id
    val studentID by lazy {
        EMClient.getInstance().currentUser
    }

//注册监听器
    val msgListener = object : MsgListenerAdapter() {

    override fun onMessageReceived(p0: MutableList<EMMessage>?) {
        presenter.addMsg(groupname,p0)
        //view层
        runOnUiThread {recycler_chat.adapter?.notifyDataSetChanged() }
    }
}


    override fun init() {
        super.init()
        initHeader()
        initRecycler()
        send.setOnClickListener { send() }
        //enter按键监听
        edit.setOnEditorActionListener{p0,p1,p2 ->
            send()
            true
        }
        //启用监听
        EMClient.getInstance().chatManager().addMessageListener(msgListener)
        //初始化聊天记录
        presenter.loadMsgs(groupname)


    }


    private fun initRecycler(){
        recycler_chat.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = MessageListAdapter(context,presenter.Msgs)

            //检测空闲状态，是否到顶，加载更多数据
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE){
                        val linearLayoutManager = layoutManager as LinearLayoutManager
                        if (linearLayoutManager.findFirstVisibleItemPosition() == 0){
                            presenter.loadMoreMsgs(groupname)
                        }
                    }
                }

            })
        }
    }

    fun send() {
        hideSoftKeyBoard()
        val msg = edit.text.trim().toString()
        presenter.sendMsg(groupname,msg)
        ToBottom()
    }

    private fun initHeader() {
        initScoreGet()
        header_back.setOnClickListener {
             finish()
             startActivity<MainActivity>()
             //获取username
     }
        //初始化每一个聊天框标题
        groupname = intent.getStringExtra("groupname")
        val titleString = String.format("群组：%s  聊天中",groupname)
        header_title.text = titleString
    }


//发送消息中、成功、失败
    override fun onStartSendMsg() {
        //刷新列表
        recycler_chat.adapter?.notifyDataSetChanged()

    }

    override fun onSendMsgSuccess() {
        recycler_chat.adapter?.notifyDataSetChanged()
        edit.text.clear()

    }


    override fun onSendMsgFailed() {
        recycler_chat.adapter?.notifyDataSetChanged()
        toast("发送失败")
    }

   /* override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(msgListener)
    }

*/
    //滚动到底部
   private fun ToBottom() {
       recycler_chat.scrollToPosition(presenter.Msgs.size - 1)
   }
//初始化加载消息，在view层的实现
    override fun onLoadMsgs() {
        recycler_chat.adapter?.notifyDataSetChanged()
        ToBottom()
    }
//加载更多数据
    override fun onMoreMsgsLoad(size: Int) {
        recycler_chat.adapter?.notifyDataSetChanged()
        recycler_chat.scrollToPosition(size)
    }
//测试：建立http链接
    private fun initScoreGet() {
        /*Http.init(this)
        header_add.setOnClickListener {
            Http.get{
                url = "http://47.107.105.126:5001/score"
                params{
                    "name" - groupname
                }
                onSuccess {

                }
                onFail {
                    toast("加载失败")
                }
            }*/
        header_add.setOnClickListener {
            //加载okhttp
            val okHttpGet = OkHttpClient()
            //在子线程中完成，防止假死
            doAsync {
                val request = Request.Builder()
                    .get()
                    .url("http://47.107.105.126:5001/search?classname=$groupname&id=$studentID")
                    .build()
                val response = okHttpGet.newCall(request).execute()
                if (response.isSuccessful){
                    //获取json的body
                    val jsonString = response.body()!!.string()
                    //调用内置的jsonObject
                    val strList = JsontOString(jsonString)
                    //主线程中生成dialog
                    uiThread {
                        val dialog = AlertDialog.Builder(it)
                        //val view = LayoutInflater.from(it).inflate(R.layout.item_score,null)
                        dialog.setTitle("总成绩："+strList[2])
                        dialog.setMessage("平时成绩："+strList[0]+"    "+"考试成绩"+strList[1])
                        //dialog.setView(view)
                        //score_nomal.text = nomalScore
                      //  score_exam.text = examScore
                    //    score_final.text = finalScore
                        dialog.setPositiveButton("返回",DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int ->
                            null
                        })
                        dialog.show()
                        print(jsonString)
                    }
                }
                    else{
                    throw IOException("Unexpected code $response")
                }
            }

        }
    }

    //从json中返回列表
    private fun JsontOString(jsonString : String): Array<String> {
        val jsonObj = JSONObject(jsonString)
        val nomalScore = jsonObj.getString("nomal").trim()
        val finalScore =jsonObj.getString("final").trim()
        val examScore = jsonObj.getString("exam").trim()
        val list = arrayOf(nomalScore,examScore,finalScore)
        return list
    }


}






package com.example.im_test.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.im_test.data.AlarmEvent
import com.example.im_test.item.EventItem

class EventListAdapter(val context: Context, val eventList :MutableList<AlarmEvent>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return EventItemViewHolder(EventItem(context))
    }

    override fun getItemCount(): Int = eventList.size


    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val gi = p0.itemView as EventItem
        gi.bindView(eventList[p1])
    }

    class EventItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}


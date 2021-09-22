//package com.klemer.doctorsforeveryone.adapter
//
//import androidx.recyclerview.widget.DiffUtil
//import com.klemer.doctorsforeveryone.model.HealthNews
//
//open class HealthNewsDiff(private val oldList: List<HealthNews>, private val newList: List<HealthNews>)
//    : DiffUtil.Callback() {
//
//    override fun getOldListSize(): Int = oldList.size
//
//    override fun getNewListSize(): Int = newList.size
//
//    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldList[oldItemPosition] == newList[newItemPosition]
//    }
//
//    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldList[oldItemPosition] == newList[newItemPosition]
//    }
//
//
//}
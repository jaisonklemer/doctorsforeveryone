package com.klemer.doctorsforeveryone.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.databinding.SearchHeaderBinding

class HeaderAdapter(private val onSearch: (String) -> Unit) : RecyclerView.Adapter<HeaderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.search_header, parent, false).apply {
            return HeaderViewHolder(this, onSearch)
        }
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1
}


class HeaderViewHolder(itemView: View, val onSearch: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {


    private val binding = SearchHeaderBinding.bind(itemView)


    fun bind() {
        binding.searchDoctors.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    if (it.length > 2) {
                        onSearch(it.toString())
                    }

                    if (it.isNotEmpty()) {
                        onSearch(it.toString())
                    }
                }
            }
            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }
}

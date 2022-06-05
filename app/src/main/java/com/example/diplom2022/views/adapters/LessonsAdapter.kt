package com.example.diplom2022.views.adapters

import android.content.Context
import com.example.diplom2022.R
import io.github.farshidroohi.AdapterRecyclerView
import kotlinx.android.synthetic.main.adapter_item.view.*

class LessonsAdapter : AdapterRecyclerView<String?>(R.layout.adapter_item, R.layout.adapter_progress_view, R.layout.adapter_item_error,R.id.btnTrayAgain) {

    override fun onBindView(viewHolder: ItemViewHolder, position: Int, context: Context, element: String?) {
        val itemView = viewHolder.itemView
        itemView.txt_title.text = "$element"
    }
}
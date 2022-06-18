package com.example.diplom2022.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom2022.R
import com.example.diplom2022.database.entities.Lesson

class ChecklistAdapter(
    private val lessons: List<String>,
    private val context: Context,
    private val onLessonClick: OnLessonClickListener
) :
    RecyclerView.Adapter<ChecklistAdapter.MyViewHolder>() {

    interface OnLessonClickListener {
        fun onLessonClick(title : String)
    }

    private var list: List<Lesson> = ArrayList<Lesson>()

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val lesson = lessons[position]

        holder.title.text = lesson
        holder.view.setOnClickListener {
            View.OnClickListener {
                onLessonClick.onLessonClick(lesson)
            }

        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.adapter_item_lesson, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageButton: ImageButton = itemView.findViewById(R.id.isFavoriteBtn)
        var title: TextView = itemView.findViewById(R.id.lesson_title)
        var view: View = itemView
    }
}
package com.example.diplom2022.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.example.diplom2022.R
import com.example.diplom2022.viewmodels.ApplicationViewModel
import io.github.farshidroohi.AdapterRecyclerView
import kotlinx.android.synthetic.main.adapter_item_lesson.view.*
import java.util.*
import kotlin.collections.ArrayList


class LessonsAdapter(
    appViewModel: ApplicationViewModel,
    viewLifecycleOwner: LifecycleOwner,
    favoritesLessonId: List<Int>,
) : AdapterRecyclerView<String?>(
    R.layout.adapter_item_lesson,
    R.layout.adapter_progress_view,
    R.layout.adapter_item_error,
    R.id.btnTrayAgain
) {

    private var favoritesLessonIdList: List<Int>? = favoritesLessonId
    private var viewModel: ApplicationViewModel? = appViewModel
    private val viewLifecycleOwnerInstance = viewLifecycleOwner

    override fun onBindView(
        viewHolder: ItemViewHolder,
        position: Int,
        context: Context,
        element: String?
    ) {
        val itemView = viewHolder.itemView
        itemView.lesson_title.text = "$element"

        viewModel?.lessons?.observe(viewLifecycleOwnerInstance) { _lessons ->
            for (el in _lessons) {
                if (el.title == element && favoritesLessonIdList?.contains(el.id)!!) {
                    viewHolder.itemView.isFavoriteBtn.visibility = View.VISIBLE
                }
            }
        }
    }
}
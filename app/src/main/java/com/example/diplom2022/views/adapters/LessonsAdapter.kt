package com.example.diplom2022.views.adapters

import android.content.Context
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.example.diplom2022.R
import com.example.diplom2022.viewmodels.ApplicationViewModel
import io.github.farshidroohi.AdapterRecyclerView
import kotlinx.android.synthetic.main.adapter_item_lesson.view.*


class LessonsAdapter(
    appViewModel: ApplicationViewModel,
    viewLifecycleOwner: LifecycleOwner,
    _email: String?
) : AdapterRecyclerView<String?>(
    R.layout.adapter_item_lesson,
    R.layout.adapter_progress_view,
    R.layout.adapter_item_error,
    R.id.btnTrayAgain
) {

    private var viewModel: ApplicationViewModel? = appViewModel
    private val viewLifecycleOwnerInstance = viewLifecycleOwner
    private val email = _email

    override fun onBindView(
        viewHolder: ItemViewHolder,
        position: Int,
        context: Context,
        element: String?
    ) {
        viewHolder.setIsRecyclable(false);
        val itemView = viewHolder.itemView
        itemView.lesson_title.text = "$element"

        viewModel?.lessons?.observe(viewLifecycleOwnerInstance) { _lessons ->
            if (_lessons.map { it.title }.contains(element)) {
                email?.let { email ->
                    viewModel?.getFavoritesList(email)
                        ?.observe(viewLifecycleOwnerInstance) { _favorites ->
                            for (el in _lessons) {
                                if (el.title == element && _favorites?.map { it.lessonId }
                                        ?.contains(el.id)!!) {
                                    println("test ${el.id}")
                                    viewHolder.itemView.isFavoriteBtn.visibility = View.VISIBLE
                                }
                            }
                        }
                }
            }
        }
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
    }
}
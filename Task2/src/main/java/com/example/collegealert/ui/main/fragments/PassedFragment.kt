package com.example.collegealert.ui.main.fragments

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collegealert.R
import com.example.collegealert.model.Task
import com.example.collegealert.ui.adapter.OnTaskItemClickListener
import com.example.collegealert.ui.adapter.TaskAdapter
import com.example.collegealert.ui.main.viewmodel.MainViewModel
import com.example.collegealert.utils.enums.TaskType

/*
* Created by Ma7mouD on Mon 30/10/2023 at 03:16 AM.
*/
class PassedFragment : Fragment(), OnTaskItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var emptyLayout: LinearLayout
    private lateinit var emptyTextView: TextView
    private lateinit var emptyIcon: ImageView

    private lateinit var viewModel: MainViewModel

    private var tasksList: MutableList<Task> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_passed, container, false)
        initComponents(view)

        viewModel.passedTasksLiveData.observe(viewLifecycleOwner) {
            tasksList = it.toMutableList()
            if (it.isNotEmpty()) {
                recyclerView.visibility = View.VISIBLE
                emptyLayout.visibility = View.GONE
                adapter.setDataToAdapter(
                    it.sortedBy { task -> task.deadlineDate }
                        .sortedBy { task -> task.deadlineStartTime },
                )
            } else {
                recyclerView.visibility = View.GONE
                emptyLayout.visibility = View.VISIBLE
            }
        }
        return view
    }

    private fun initComponents(view: View) {
        prepareViewModel()
        recyclerView = view.findViewById(R.id.passed_rv)
        emptyLayout = view.findViewById(R.id.passed_layout)
        emptyTextView = view.findViewById(R.id.passed_tv)
        emptyIcon = view.findViewById(R.id.passed_iv)

        setupRecyclerView()
    }

    private fun prepareViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
        )[MainViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(this.requireContext(), listOf(), this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val itemTouchHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder,
                ): Boolean {
                    // Not needed for swipe actions, so return false
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition

                    when (direction) {
                        ItemTouchHelper.LEFT -> {
                            viewModel.removeTask(tasksList[position].id!!)
                            adapter.notifyItemRemoved(position)
                        }
                        ItemTouchHelper.RIGHT -> {
                            viewModel.handleDone(tasksList[position].id!!)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean,
                ) {
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        val itemView = viewHolder.itemView

                        val deleteIcon = ContextCompat.getDrawable(
                            this@PassedFragment.requireContext(),
                            R.drawable.round_delete_forever_24,
                        )
                        val doneIcon = ContextCompat.getDrawable(
                            this@PassedFragment.requireContext(),
                            R.drawable.round_check_circle_24,
                        )
                        val intrinsicWidth = deleteIcon?.intrinsicWidth ?: 0
                        val intrinsicHeight = deleteIcon?.intrinsicHeight ?: 0

                        val background: Drawable
                        val icon: Drawable
                        val iconMargin = (itemView.height - intrinsicHeight) / 2

                        if (dX > 0) {
                            // Swiping to the right (edit action)
                            background = ColorDrawable(
                                ContextCompat.getColor(
                                    this@PassedFragment.requireContext(),
                                    R.color.green,
                                ),
                            )
                            icon = doneIcon!!
                            icon.setBounds(
                                itemView.left + iconMargin,
                                itemView.top + iconMargin,
                                itemView.left + iconMargin + intrinsicWidth,
                                itemView.bottom - iconMargin,
                            )
                        } else {
                            // Swiping to the left (delete action)
                            background = ColorDrawable(
                                ContextCompat.getColor(
                                    this@PassedFragment.requireContext(),
                                    R.color.red,
                                ),
                            )
                            icon = deleteIcon!!
                            icon.setBounds(
                                itemView.right - iconMargin - intrinsicWidth,
                                itemView.top + iconMargin,
                                itemView.right - iconMargin,
                                itemView.bottom - iconMargin,
                            )
                        }

                        background.setBounds(
                            itemView.left + 50,
                            itemView.top + 50,
                            itemView.right - 50,
                            itemView.bottom - 50,
                        )

                        background.draw(c)
                        icon.draw(c)
                    }

                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive,
                    )
                }
            })

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onTaskItemClick(position: Int) {
        viewModel.handleTaskItemClick(position, TaskType.PASSED)
    }
}

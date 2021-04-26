package com.example.mytodolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.Data.Tasks
import com.google.android.material.snackbar.Snackbar

/**
 * A fragment representing a list of Items.
 */
class TaskFragment : Fragment(), ToDoListListner,
    DelateDialogFragment.onDelateDialogInteractionListner {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        val eventListListener = this
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager =  LinearLayoutManager(context)
                adapter = MyTaskRecyclerViewAdapter(Tasks.ITEMS,eventListListener)
            }
        }
        return view
    }

    override fun onItemClick(position: Int) {
        val actionTaskToDisplayTaskFragmen =
            TaskFragmentDirections.actionTaskFragmentToDisplayTaskFragment(Tasks.ITEMS.get(position))
        findNavController().navigate(actionTaskToDisplayTaskFragmen)
    }

    override fun onItemLongClick(position: Int) {
        showDelateDialog(position)
    }

    private fun showDelateDialog(position: Int) {
        val deleteDialog = DelateDialogFragment.newInstance(Tasks.ITEMS.get(position).title, position, this)
        deleteDialog.show(requireActivity().supportFragmentManager,"DeleteDialog")
    }

    override fun onDialogPositiveClick(pos: Int?) {
        Tasks.ITEMS.removeAt(pos!!)
        Snackbar.make(requireView(), "Task Delate", Snackbar.LENGTH_LONG).show()
        notifyDataSetChanged()
    }

    private fun notifyDataSetChanged() {
        val rvAdapter = view?.findViewById<RecyclerView>(R.id.list)?.adapter
        rvAdapter?.notifyDataSetChanged()
    }

    override fun onDialogNegativeClick(pos: Int?) {
        Snackbar.make(requireView(), "Delete cancel", Snackbar.LENGTH_LONG).setAction("Redo", View.OnClickListener { pos!! }).show()
    }
}
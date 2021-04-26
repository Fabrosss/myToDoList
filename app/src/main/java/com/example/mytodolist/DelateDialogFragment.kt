package com.example.mytodolist

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val TASK_NAME_PARAM = "task name"
private const val TASK_POS_PARAM = "task pos"


/**
 * A simple [Fragment] subclass.
 * Use the [DelateDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DelateDialogFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var taskNameParam: String? = null
    private var taskPosParam: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            taskNameParam = it.getString(TASK_NAME_PARAM)
            taskPosParam = it.getInt(TASK_POS_PARAM)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder:  AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage(getString(R.string.delateEntry))
        builder.setPositiveButton("Confirm",DialogInterface.OnClickListener { dialogInterface, i ->
            mListner?.onDialogPositiveClick(taskPosParam)
        })
        builder.setNegativeButton("Discard", DialogInterface.OnClickListener{ dialogInterface, i->
            mListner?.onDialogNegativeClick(taskPosParam)
        })
        return builder.create()
    }

    interface onDelateDialogInteractionListner{
        fun onDialogPositiveClick(pos: Int?)
        fun onDialogNegativeClick(pos: Int?)
    }
    lateinit var  mListner: onDelateDialogInteractionListner
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param name Parameter 1.
         * @param pos Parameter 2.
         * @return A new instance of fragment DelateDialogFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(name: String, pos: Int, interactionListner: onDelateDialogInteractionListner) =
            DelateDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(TASK_NAME_PARAM, name)
                    putInt(TASK_POS_PARAM, pos)
                }
            }
    }
}
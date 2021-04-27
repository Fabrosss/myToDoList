package com.example.mytodolist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodolist.Data.IMPORTANCE
import com.example.mytodolist.Data.Tasks


/**
 * A simple [Fragment] subclass.
 * Use the [AddTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTaskFragment : Fragment() {
    lateinit var  importatnceGroup: RadioGroup
    lateinit var  titleInput: EditText
    lateinit var descriptionInput: EditText
    val args: AddTaskFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        importatnceGroup = view.findViewById(R.id.importanceGroup)
        titleInput = view.findViewById(R.id.titleInput)
        descriptionInput = view.findViewById(R.id.descriptionInput)
        val saveButton = view.findViewById<ImageButton>(R.id.saveButton)
        saveButton.setOnClickListener{
            saveTask()
        }
        titleInput.setText(args.taskToEdit?.title ?: " ")
        descriptionInput.setText(args.taskToEdit?.description)
        when(args.taskToEdit?.importatnce){
            IMPORTANCE.LOW -> importatnceGroup.check(R.id.low_radioButton)
            IMPORTANCE.NORMAL -> importatnceGroup.check(R.id.Normal_radioButton)
            IMPORTANCE.HIGH -> importatnceGroup.check(R.id.High_radioButton)
            null -> importatnceGroup.check(R.id.Normal_radioButton)
        }
    }
//
    private fun saveTask() {
        var title: String = titleInput.text.toString()
        var description: String = descriptionInput.text.toString()
        val importance = when(importatnceGroup.checkedRadioButtonId){
            R.id.low_radioButton -> IMPORTANCE.LOW
            R.id.Normal_radioButton -> IMPORTANCE.NORMAL
            R.id.High_radioButton -> IMPORTANCE.HIGH
            else -> IMPORTANCE.LOW
        }
        if(title.isEmpty()) title = getString(R.string.defoultTaskTitle) + "${Tasks.ITEMS.size + 1}"
        if(description.isEmpty()) description = getString(R.string.No_Desc_mess)

        val taskItem = Tasks.TaskItem(
            { title + description}.hashCode().toString(),
            title,
            description,
            importance
        )
        if(!args.edit)
        Tasks.addTask(taskItem)
        else
        {
            Tasks.updateTask(args.taskToEdit!!, taskItem)
        }
        val inputMethodMenager: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodMenager.hideSoftInputFromWindow(titleInput.windowToken, 0)
        findNavController().popBackStack(R.id.taskFragment,false)
    }

}
package com.deccovers.todomvvm.ui.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.deccovers.todomvvm.R
import com.deccovers.todomvvm.data.TodoEntry
import com.deccovers.todomvvm.databinding.FragmentAddBinding
import com.deccovers.todomvvm.ui.list.TodoListViewModel

class AddFragment : Fragment() {

    private val todoListViewModel: TodoListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAddBinding.inflate(inflater)

        val myAdapter = ArrayAdapter (
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.priorities)
        )

        binding.apply {
            spinnerAddPriority.adapter = myAdapter
            btnAddTodo.setOnClickListener {
                if (TextUtils.isEmpty(etTodoTitle.text)) {
                    Toast.makeText(requireContext(), "Fill up entry first", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val titleString = etTodoTitle.text.toString()
                val priorityInt = spinnerAddPriority.selectedItemPosition

                val todoEntry = TodoEntry(
                    title = titleString,
                    priority = priorityInt,
                    timestamp = System.currentTimeMillis()
                )

                todoListViewModel.insertTodo(todoEntry)
                Toast.makeText(requireContext(), "Todo added!", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_addFragment_to_listFragment)
            }
        }
        return binding.root
    }

}
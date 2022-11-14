package com.deccovers.todomvvm.ui.update

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.deccovers.todomvvm.R
import com.deccovers.todomvvm.data.local.TodoEntry
import com.deccovers.todomvvm.databinding.FragmentUpdateBinding
import com.deccovers.todomvvm.ui.list.TodoListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : Fragment() {

    private val todoListViewModel: TodoListViewModel by viewModels()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        binding.apply {
            etUpdateTitle.setText(args.todoEntry.title)
            spinnerUpdatePriority.setSelection(args.todoEntry.priority)

            btnUpdateTodo.setOnClickListener {
                if (TextUtils.isEmpty(etUpdateTitle.text)) {
                    Toast.makeText(requireContext(), "Fill up todo first", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val updatedTitle = etUpdateTitle.text.toString()
                val updatedPriority = spinnerUpdatePriority.selectedItemPosition

                val todoEntry = TodoEntry (
                    args.todoEntry.id,
                    updatedTitle,
                    updatedPriority,
                    args.todoEntry.timestamp
                )

                todoListViewModel.updateTodo(todoEntry)
                Toast.makeText(requireContext(), "Todo updated!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
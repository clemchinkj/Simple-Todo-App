package com.deccovers.todomvvm.ui.list

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.deccovers.todomvvm.R
import com.deccovers.todomvvm.databinding.FragmentListBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class ListFragment : Fragment() {

    private val todoListViewModel: TodoListViewModel by viewModels()
    private lateinit var todoListAdapter: TodoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)

        val binding = FragmentListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = todoListViewModel

        todoListAdapter = TodoListAdapter(TodoClickListener { todoEntry ->
            findNavController().navigate(ListFragmentDirections.actionListFragmentToUpdateFragment(todoEntry))
        })

        viewLifecycleOwner.lifecycleScope.launch {
            todoListViewModel.getAllTodos.collect {
                todoListAdapter.submitList(it)
            }
        }

        binding.apply {
            binding.rvTodoList.adapter = todoListAdapter

            fabTodoList.setOnClickListener {
                findNavController().navigate(R.id.action_listFragment_to_addFragment)
            }
        }

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val todoEntry = todoListAdapter.currentList[position]
                todoListViewModel.deleteTodo(todoEntry)

                Snackbar.make(binding.root, "Deleted todo", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        todoListViewModel.insertTodo(todoEntry)
                    }
                    show()
                }
            }
        }).attachToRecyclerView(binding.rvTodoList)

        // Set fragment toolbar
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.todo_menu, menu)

                val searchItem = menu.findItem(R.id.menu_action_search)
                val searchView = searchItem.actionView as SearchView

                searchView.isIconified = false
                searchView.setOnQueryTextFocusChangeListener { view, hasFocus ->
                    if (hasFocus) {
                        showInputMethod(view.findFocus())
                    }
                }

                searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText != null) {
                            runQuery(newText)
                        }
                        return true
                    }

                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.menu_action_delete_all -> {
                        showDeleteAllDialog()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        hideKeyboard(requireActivity())
        return binding.root
    }

    private fun showInputMethod(view: View) {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }

    fun runQuery(query: String) {
        val searchQuery = "%$query%"

        viewLifecycleOwner.lifecycleScope.launch{
            todoListViewModel.searchDatabase(searchQuery).collect {
                todoListAdapter.submitList(it)
            }
        }
    }

    private fun hideKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = activity.currentFocus
        currentFocusedView.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusedView?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    private fun showDeleteAllDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete all todos")
            .setMessage("Are you sure?")
            .setPositiveButton("Yes") {dialog, _ ->
                todoListViewModel.deleteAllTodos()
                dialog.dismiss()
            }
            .setNegativeButton("No") {dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

}
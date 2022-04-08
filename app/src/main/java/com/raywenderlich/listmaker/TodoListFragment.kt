package com.raywenderlich.listmaker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_todo_list.*


class TodoListFragment : Fragment(), TodoListAdapter.TodoListClickListener {

    private lateinit var todoListRecyclerView : RecyclerView

    private lateinit var listDataManager : ListDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /*
    This method is when the fragment acquires a layout it must have in order to
    be presented within the activity
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    /*
    * onViewCreated is called immediately after onCreate view*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            listDataManager = ViewModelProviders.of(this).get(ListDataManager::class.java)
            //ViewModelProviders - add an dependency to your gradle file through bulb
            //get - this is where you define the class that you're getting

           // listDataManager = ListDataManager(it)
        }

        //Before you create your recycler view, add the following
        val lists = listDataManager.readLists()

        //get a reference to your RecyclerView
        todoListRecyclerView = view.findViewById(R.id.lists_recyclerview)

        //The RecyclerView needs to know about your layout when placing items.
        todoListRecyclerView.layoutManager = LinearLayoutManager(activity)

        //you need to assign your adapter to your RecyclerView
        //todoListRecyclerView.adapter = TodoListAdapter()

        /*when the user taps on a item in the RecyclerView, it's going to notify the fragment,
        which then needs to notify the activity.
        */
        todoListRecyclerView.adapter = TodoListAdapter(lists, this)

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { _ ->
            //you now add a new to-do list item
            //The first thing you need to do is get your adapter

            // val adapter = todoListRecyclerView.adapter (todoListRecyclerView.adapter - just a regular recycler view adapter)

            // you need to do a cast to access your own to-do list adapter
            /*val adapter = todoListRecyclerView.adapter as TodoListAdapter
            adapter.addNewItem()*/

            showCreateTodoListDialog()

            /*fab.setOnClickListener { view ->
            Since you don't wanna use the view, provide an _
            fab.setOnClickListener { _ -> */
        }
    }

/*
    onAttach - is the first life cycle method run by a fragment. This method is run when
    the fragment is first associated with an activity, which gives you a chance to set up
    anything required before the fragment is created.
*/
    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
            listDataManager = ListDataManager(context)
        }
    }*/

    /*OnDetach is called when a fragment is no longer attached to an activity, which
    * could be due to an activity being destroyed or the fragment being removed*/

    /*override fun onDetach() {
        super.onDetach()
        //The listener is set to null as the activity is no longer available
        listener = null
    }*/

    /*
    This interface must be implemented by activities that contain this
    fragment to allow an interaction in this fragment to be communicated
    to the activity and potentially other fragments contained in that activity
    */

    interface OnFragmentInteractionListener {
        fun onTodoListClicked(list: TaskList)
    }

    //used to create an instance of your fragment
    companion object {
        fun newInstance(): TodoListFragment {
            return TodoListFragment()
        }
    }

    override fun listItemClicked(list: TaskList) {
        showTaskListItems(list)
        /*What's happening here is when the user taps on the row, listItemClicked will be fired
        and you'll call showTaskListItems.*/

        /*view?.let {
            it.findNavController().navigate(R.id.action_todoListFragment_to_taskDetailFragment)
        }*/

        /*when the user taps on the ViewHolder, this method is called and then it notifies
        its listener, that something has happened. And that listener is the activity.*/
        //listener?.onTodoListClicked(list)
    }

    fun addlist(list: TaskList) {
        listDataManager.saveList(list)
        val todoAdapter = todoListRecyclerView.adapter as TodoListAdapter
        todoAdapter.addList(list)
    }

    fun saveList(list: TaskList) {
        listDataManager.saveList(list)
        updateLists()
    }

    private fun showCreateTodoListDialog(){
        /*
        A fragment can gain access to a context if it's available by using the
        activity property, but this is a nullable type. So you'll have to put this
        with a let block here
         */
        activity?.let {
            //create some variables to hold some strings
            val dialogTitle = getString(R.string.name_of_list)
            val positiveButtonTitle = getString(R.string.create_list)
            val myDialog = AlertDialog.Builder(it)

            // next you need an EditText()
            val todoTitleEditText = EditText(it)

            //now you need to determine how the keyboard behaves when the user taps it.
            //in this case you just want a regular keyboard to show up.
            todoTitleEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
            // TYPE_CLASS_TEXT - We want regular keyboard to show up
            // TYPE_TEXT_FLAG_CAP_WORDS - each word to capitalized

            myDialog.setTitle(dialogTitle)
            myDialog.setView(todoTitleEditText)

            myDialog.setPositiveButton(positiveButtonTitle) {
                /*you're going to pass in a listener. This listener takes a dialog and this is the dialog that you're using and an
                int, letting you know which button was tapped. You don't need to know which button was tapped was tapped in this
                method, so you can just use an underscore*/
                    dialog, _ ->
                /*
                get the text from the edit text, and then add it to the list. To do this, you need to access to your adapter
                and you do it in the set positive button closure.
                 */
                /*create variable for your adapter. You can get this from your recycler view. The recycler view has an adapter
                property, and like before, you have to cast it.*/

                //  val adapter = todoListRecyclerView.adapter as TodoListAdapter

                //First you need to create an empty task list passing in the edit text as title
                val list = TaskList(todoTitleEditText.text.toString())

                //saving it
                //listDataManager.saveList(list)

                //now you need to update your recycler view with the list.
                //adapter.addList(list)

                //  adapter.addNewItem(todoTitleEditText.text.toString())

                /*delegate all of the managing of the RecyclerView and saving the data
                to your fragment*/
                addlist(list)

                //and once they tap this, you wanna dismiss the dialog
                dialog.dismiss()

                //brought to the new activity
                showTaskListItems(list)

            }

            //create and show the dialog
            myDialog.create().show()
        }
    }

    private fun showTaskListItems(list : TaskList){
        view?.let {
            //Trigger the navigation
            val action = TodoListFragmentDirections.actionTodoListFragmentToTaskDetailFragment(list.name)
            it.findNavController().navigate(action)

            //it.findNavController().navigate(R.id.action_todoListFragment_to_taskDetailFragment)
        }
    }

    private fun updateLists() {
        val lists = listDataManager.readLists()
        //create new adapter using these lists
        //This is just another way of refreshing your recycler view
        todoListRecyclerView.adapter = TodoListAdapter(lists, this)
    }
}
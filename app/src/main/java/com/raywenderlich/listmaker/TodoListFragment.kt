package com.raywenderlich.listmaker

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class TodoListFragment : Fragment(), TodoListAdapter.TodoListClickListener {

    private var listener: OnFragmentInteractionListener? = null

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
    }

/*
    onAttach - is the first life cycle method run by a fragment. This method is run when
    the fragment is first associated with an activity, which gives you a chance to set up
    anything required before the fragment is created.
*/
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
            listDataManager = ListDataManager(context)
        }
    }

    /*OnDetach is called when a fragment is no longer attached to an activity, which
    * could be due to an activity being destroyed or the fragment being removed*/

    override fun onDetach() {
        super.onDetach()
        //The listener is set to null as the activity is no longer available
        listener = null
    }

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
        /*when the user taps on the ViewHolder, this method is called and then it notifies
        its listener, that something has happened. And that listener is the activity.*/
        listener?.onTodoListClicked(list)
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

    private fun updateLists() {
        val lists = listDataManager.readLists()
        //create new adapter using these lists
        //This is just another way of refreshing your recycler view
        todoListRecyclerView.adapter = TodoListAdapter(lists, this)
    }
}
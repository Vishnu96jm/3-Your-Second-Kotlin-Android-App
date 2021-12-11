package com.raywenderlich.listmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/*
Extend the RecyclerView Adapter. It needs a type of ViewHolder. For this you're going to user your
To-Do List ViewHolder.
 */

class TodoListAdapter : RecyclerView.Adapter<TodoListViewHolder>() {

    //create an instance variable to contain your todo lists.
    private val todoLists = arrayOf("Android Development","House Work", "Errands", "Shopping")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        /*onCreateViewHolder meant to return a new view holder. Since the recycler view recycles its own rows, this
        method may be called only a dozen times even when working with lists of thousand rows.*/

        /*Part 1 : 8; your RecyclerView needs a row, so it calls onCreateViewHolder. In this method, the first thing
        you do is inflate your layout, then you create a new ViewHolder object passing in your layout. Then return
        your new ViewHolder object.*/

        /*1. create a new layout for your ViewHolder.
        * 2. create an instance of ViewHolder. Inflate the layout and pass it to the
ViewHolder's constructor.*/

        /*A context object represents all the various resources available to an application, and it gets
        that content from the ViewGroup.*/
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_list_view_holder, parent, false)

        /*
        todo_list_view_holder - This is your cell layout
        root.ViewGroup - parent (A ViewGroup is simply a view that contains other views)
        false - you pass in the ViewGroup provided to the method but you don't want to automatically
        attach the ViewHolder to it. That's the RecyclerView's job.
         */

        //create your ViewHolder from inflated view
        return TodoListViewHolder(view)

    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        /*onBindViewHolder is meant to customize each row according to your data. This method is called for
        * each row in the list. */

        /*Part 1 : 9;
        when your RecyclerView needs an updated ViewHolder,it will call the onBindViewHolder method,
        passing in a new Recycler ViewHolder. The method also receives the current position in the list.
        First item in the list is position 0*/

        /*
        part 1 : 10;
        when this method is called, you're passed in a ViewHolder. And this ViewHolder is a type of
        TodoListViewHolder. Using this object, you can now access the TextViews that you just exposed.
         */

        //(position + 1) The position is zero-based, so you need to add 1

        holder.listPositionTextView.text = (position + 1).toString()
        holder.listTitleTextView.text = todoLists[position]

    }

    override fun getItemCount(): Int {
        /*
        Tells the RecyclerView, how many items are in your list.
         */
        return todoLists.size
    }

}
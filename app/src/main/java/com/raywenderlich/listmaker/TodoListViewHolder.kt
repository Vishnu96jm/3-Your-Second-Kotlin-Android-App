package com.raywenderlich.listmaker

import android.view.TextureView
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/*
   (itemView : View) - you need to add a primary constructor. This constructor will take in a view and it will pass
   that view into its super-class constructor.

   RecyclerView.ViewHolder(itemView) - Calling super class, passing item view

   with that you have your ViewHolder all set up

   ViewHolder - visual appearance of the row
    */

class TodoListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    /* part 1 ;9 - you need to add two properties so that you can change the actual
    text within that view.*/

    var listPositionTextView = itemView.findViewById<TextView>(R.id.itemNumber)
    var listTitleTextView = itemView.findViewById<TextView>(R.id.itemString)
}
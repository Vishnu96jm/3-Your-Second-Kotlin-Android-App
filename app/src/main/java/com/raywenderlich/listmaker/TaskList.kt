package com.raywenderlich.listmaker


/*
So far, you've been sotring all of your to do lists in a single list object. Since
you'll be adding a sub list of task items, it's best to create a class for it.

 */

//The task list will take a name, then an array of tasks.
//Basically, if the caller doesn't provide a list, you'll create an empty list for them
class TaskList(val name : String, val tasks: ArrayList<String> = ArrayList()) {

    /*Now that you have created to do list object, you can write the code to save and
    read it to disk. To do this you're going to create another class. create class ListDataManager*/
}
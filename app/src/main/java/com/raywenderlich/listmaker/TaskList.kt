package com.raywenderlich.listmaker

import android.os.Parcel
import android.os.Parcelable


/*
So far, you've been sotring all of your to do lists in a single list object. Since
you'll be adding a sub list of task items, it's best to create a class for it.

 */

//The task list will take a name, then an array of tasks.
//Basically, if the caller doesn't provide a list, you'll create an empty list for them
class TaskList(val name : String, val tasks: ArrayList<String> = ArrayList()) : Parcelable{
    /*Now that you have created to do list object, you can write the code to save and
    read it to disk. To do this you're going to create another class. create class ListDataManager*/

    /*
    This constructor will take in a parcel and it will call the primary constructor.
     */
    constructor(parcel : Parcel) : this(
        //first it'll find out the name of the list and we'll just unwrap those
        parcel.readString()!!,
        //and then it will get the list itself, and you have to unwrap that as well
        parcel.createStringArrayList()!!
    )

    companion object CREATOR: Parcelable.Creator<TaskList>{
        //create the parcel using the source passed into it
        override fun createFromParcel(source: Parcel): TaskList = TaskList(source)

        //it returns an array of TaskLists
        override fun newArray(size: Int): Array<TaskList?> = arrayOfNulls(size)

        /*override fun createFromParcel(source: Parcel?): TaskList {
            TODO("Not yet implemented")
        }

        override fun newArray(size: Int): Array<TaskList> {
            TODO("Not yet implemented")
        }*/

    }

    /*
    describeContents function is used with what are known as file descriptors, and since you're
    not dealing with file descriptors, you provide a zero value.
     */
    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        //you are converting your task list into a parcel
        dest.writeString(name)
        //and next you're gonna write a StringList and you'll pass in the tasks
        dest.writeStringList(tasks)
    }

    /*override fun describeContents(): Int {
        TODO("Not yet implemented")
    }*/

    /*override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }*/


}
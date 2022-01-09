package com.raywenderlich.listmaker

import android.content.Context
//import android.preference.PreferenceManager
import androidx.preference.PreferenceManager

class ListDataManager(private val context: Context) {
    /*To get started, you'll write the saving method. This will be stored in a
    method called saveList*/

    fun saveList(list : TaskList){
        //To save, you need to get a reference to shared prefs
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()

        /*The actual preference manager isn't deprecated. it's been migrated to AndroidX.
        So import the AndroidX preference manager.

        1. Migrate to AndroidX - https://developer.android.com/jetpack/androidx/migrate
        2. choose support library class mappings from left
        3. Search for preference manger; copy this androidx.preference from
        "androidx.preference.PreferenceManager"
        4. open up module gradle file; under dependencies, add another implementation
        implementation 'androidx.preference:preference:1.1.0' (set it 1.1.0, and then update through bulb)

        */

        /*
        now to save your list, the preference manager doesn't save array lists. It does save sets.
        A set is like an array list, but it doesn't contain duplicates. so you need to convert the
        list to a set.
         */

        sharedPrefs.putStringSet(list.name+" Hi", list.tasks.toHashSet())
        sharedPrefs.apply()
    }

    //To read a list, you need to create a read list method.
    //This will return an array list of task list. So it's a list of tasks lists
    fun readLists() : ArrayList<TaskList>{
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)

        //The contents contain a map of keys and values
        val contents = sharedPrefs.all

        //Then create a list to hold your task list
        val taskLists = ArrayList<TaskList>()

        //loop through the keys
        for(taskList in contents){
            //first, you get the saved hash set and convert it into an arrayList
            val taskItems = ArrayList(taskList.value as HashSet<String>)

            //then you create a task list from it
            val list = TaskList(taskList.key, taskItems)

            //And finally, you add it to the array
            taskLists.add(list)
        }
        return taskLists
    }
        // You now have an object that can read and write data.
}
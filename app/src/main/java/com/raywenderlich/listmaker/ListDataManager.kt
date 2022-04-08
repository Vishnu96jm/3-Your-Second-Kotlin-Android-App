package com.raywenderlich.listmaker

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
//import android.preference.PreferenceManager
import androidx.preference.PreferenceManager

/*In computer science, the data driving your app is known as the Model. The Model just doesn't have to be shared prefs, it's
whatever holds the backing data.
For example, if you are reading and writing to a remote service, then that remote service is the model.
If you are just storing your data in classes, those objects represent your model.

The view is the visual elements
on the screen. Typically you want your model data to appear on the view.
In a profile screen, you may want to show the username which is contained in a user model object.

The Viewmodel is an object that lives between the two objects. It has as special job, it talks to the view and the model.

In your case, you want to show task lists, so instead of your fragments asking the data manager represented by a plain
old class, the fragments will ask the list data manager represented by a viewmodel.

One of the benefits about working with a viewmodel is that they persist through configuration changes. You don't need to
save your model state and restore it every time a user rotates their phone. The viewmodel manages that for you. Viewmodels
also exist in just one activity. If you go to another activity, you end up with another viewmodel, but you've just refactored your
app to use a single activity, so that's no longer an issue.

Android provides two types of viewmodels
1. A regular ViewModel - use for regular data references
2. AndroidViewModel - Use if you need to hold a reference to an application context

Since you need the context for shared prefs, you'll use an Android ViewModel in this demo.

Convert ListDataManager to use a ViewModel*/

 /*AndroidViewModel(application) - this is required so that you can keep a reference
 * to the context inside of your ViewModel.*/
class ListDataManager(application : Application) : AndroidViewModel(application) {

//     Now for this to work, you need a property for your context.
     private val context = application.applicationContext


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

        sharedPrefs.putStringSet(list.name, list.tasks.toHashSet())
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
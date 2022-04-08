package com.raywenderlich.listmaker

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class TaskDetailFragment : Fragment() {

    lateinit var list: TaskList

    lateinit var taskListRecyclerView: RecyclerView
    lateinit var addTaskButton : FloatingActionButton

    /*The nav graph also allows you to pass data to destinations, and this is by a mechanism known as safe args. With safe args,
you determine the name and type of the data and then you can add it as part of the action. The receiving destination can
access this data and use it.
But here's the thing. Safe args is meant for small pieces of data. It's not meant to contain objects. For example, instead of passing a user object, you would pass in the user id in which then that fragment could get the object based off of that id.
Remember, you're no longer working with multiple activities, you're using fragments that share the same view model. In your
case, you'll pass in the task list name and retrieve the correct task list using it. Once you make the change, you can then save
that task list in that fragment and then navigate back.

First, you need to update your TaskDetailFragment to use the new view model.
*/
    lateinit var listDataManager: ListDataManager

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //check to see if there are any arguments
        arguments?.let {
            list = it.getParcelable(ARG_LIST)!!
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listDataManager = ViewModelProviders.of(this).get(ListDataManager::class.java)

        arguments?.let {
            val args = TaskDetailFragmentArgs.fromBundle(it)

            /*filter on those lists by the name. call this filter, passing in the list.
            if the list.name == args.listString, then return it. And you wanna get the
            first one [0].
            * */
            list = listDataManager.readLists().filter { list -> list.name == args.listString }[0]
        }

        activity?.let {
            taskListRecyclerView = view.findViewById(R.id.task_list_recyclerview)
            taskListRecyclerView.layoutManager = LinearLayoutManager(it)
            taskListRecyclerView.adapter = TaskListAdapter(list)
            it.toolbar?.title = list.name

            addTaskButton = view.findViewById(R.id.add_task_button)
            addTaskButton.setOnClickListener {
                showCreateTaskDialog()
            }
        }

        //listDataManager = ViewModelProviders.of(this).get(ListDataManager::class.java)
        /*Now you can access your task list, but first you must pass the data. To do this, you
        must first add safe args to your project. Open up your build.gradle file (project) and
        add the following classpath.
        classpath "androidx.navigation:navigation-safe-args-gradle-plugin:2.1.0"

        Now, open up your build.gradle Module file, scroll up to the top and add plugin
        apply plugin: 'androidx.navigation.safeargs.kotlin'

        open up your nav_graph.xml, select the destination (taskDetailFragment). Right side there is
        a section called, Arguments.
        Click +, Name - list_string, type - String, click Add. And at this point your safe args will
        be generated for you. And you can see the generated files under the java (generated) folder.
        They are TaskDetailFragmentArgs, TodoListFragmentDirections.
        if you see just one, Build -> clean project. So this is auto-generated code.
        * */
    }

    private fun showCreateTaskDialog() {
        activity?.let {
            val taskEditText = EditText(it)
            taskEditText.inputType = InputType.TYPE_CLASS_TEXT
            AlertDialog.Builder(it)
                .setTitle(R.string.task_to_add)
                .setView(taskEditText)
                .setPositiveButton(R.string.add_task){
                    /*_> : button that was pressed. but you don't need that, so just put that
                   as an underscore*/
                        dialog, _ ->
                    val task = taskEditText.text.toString()
                    list.tasks.add(task)
                    //when it's done, you can simply dismiss the dialog

                    listDataManager.saveList(list)
                    /*now you can immediately save. That's one of the benefits of working with a
                    single activity. You can now access the ViewModel in any of the fragments.
                    So you don't need to keep on passing things around when you have access into
                    every fragment.*/

                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    companion object {

        private val ARG_LIST = "list"

        fun newInstance(list: TaskList) : TaskDetailFragment {
            //create a bundle
            //your bundle is just an object that contains key-value pairs.
            val bundle = Bundle()
            //now, you need a way to access your list within the bundle - ARG_LIST

            //put the list into the bundle
            bundle.putParcelable(ARG_LIST, list)

            //put the bundle inside of the fragment and you use the argument property
            val fragment = TaskDetailFragment()
            fragment.arguments = bundle
            return fragment


        }
    }
}
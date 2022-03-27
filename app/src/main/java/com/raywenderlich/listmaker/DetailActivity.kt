package com.raywenderlich.listmaker

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

/*An intent is an object that can request an action from another component.
You can use intents to launch other activities, you can use them to respond to incoming emails
or use them to broadcast a message across the Android system, that way other apps can respond.

There are two types of intents
1. implicit intents - lets the system determine the response. For instance, imagine your app
downloads a PDF and you provide an option for the viewer to view it. Instead of writing your
own PDF rendering activity, you can instead opt to use an implicit intent. When the user taps on
the PDF to view it the system can search for any apps that respond to the intent. When found, it
then launches the app showing your PDF. When the user is finished viewing the PDF, they simply
press the back button, and they are back in your app.

2. Explicit intent - states the exact resource that handles the action, whereas the implicit
intent leaves it up to the system and thus the user, the explicit intent dictates who and what
should be done. For instance, you may have a street address that renders fine in Google maps but
messes up other mapping applications. By using an explicit intent you dictate that Google Maps is
used to open the address.

Typically, you'll use intents to launch your own activities or you can use them to download a file
in the background.*/

/*You may be wondering, how does Android know what kinds of messages your app responds to? That is
handled by intent-filters declared in your app manifest file. The intent filter specifies the action,
data and category for your activity.

The action is the intent action accepted. This could be a standard action or you could define your own.
For eg, if you wanted your activity to respond to email composing, you could set the action to
android.intent.action.SEND. The Android official documentation provides a large list of standard events.

The category determines what kind of component responds to your intent. For instance, you may have
a text file that contains html. You could have a web browser view it or a text editor edit it. By
providing the category it determines the type of activity used to view it. If you want to view the
file in a web browser, you'd set the category to category_browseable. The intent class provides all
the standard activities. When adding categories, you add multiple categories if necessary. Some intents
declare a data. The data determines the type of data that your activity accepts. For eg, if your activity
works with text files, you can set the data to be text / plain. Of course, if your activity doesn't take any
data, then you don't need to provide the data.*/


/*
When creating an intent oftentimes that intent can be supplied data. For instance, if you were creating an
intent to open a web page, you'd provide the web page address to the intent. You can add all sorts of data
to an extra.

What happens to your old activity when you launch a new one?
When you launch activities they're placed on a stack. You can think of it like a stack of plates. The topmost
activity on the stack is the current one. When you launch activities, you put activities on the stack. When you
press the back button you pop the activity off the stack.
 */

//you want to write some code to launch your new intent.
class DetailActivity : AppCompatActivity() {

    lateinit var list: TaskList
    lateinit var taskListRecyclerView: RecyclerView
    lateinit var addTaskButton : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY) as TaskList
        title = list.name

        taskListRecyclerView = findViewById(R.id.task_list_recyclerview)
        taskListRecyclerView.layoutManager = LinearLayoutManager(this)
        taskListRecyclerView.adapter = TaskListAdapter(list)

        addTaskButton = findViewById(R.id.add_task_button)
        addTaskButton.setOnClickListener {
            showCreateTaskDialog()
        }
    }

    //you'll send the results in onBackPressed()
    override fun onBackPressed() {
        //create a bundle to store things
        val bundle = Bundle()
        bundle.putParcelable(MainActivity.INTENT_LIST_KEY, list)

        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)
        //and then super.onBackPressed() occurs

        //now the super is gonna go last for this method
        super.onBackPressed()
    }

    private fun showCreateTaskDialog() {
        val taskEditText = EditText(this)
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT
        AlertDialog.Builder(this)
            .setTitle(R.string.task_to_add)
            .setView(taskEditText)
            .setPositiveButton(R.string.add_task){
                 /*_> : button that was pressed. but you don't need that, so just put that
                as an underscore*/
                dialog, _ ->
                    val task = taskEditText.text.toString()
                    list.tasks.add(task)
                //when it's done, you can simply dismiss the dialog
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
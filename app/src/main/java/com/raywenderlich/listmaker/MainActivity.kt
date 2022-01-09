package com.raywenderlich.listmaker

import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

/*
RecyclerView:
The RecyclerView is composed of three components: a RecyclerView, an adapter and a ViewHolder.
The RecyclerView itself manages the presentation of the lists. It uses a layout manager to
determine how to display those items.

RecyclerView needs to talk to your data source
When working with a RecyclerView, you need to provide a data source. The data source provides the content
for the RecyclerView.
The RecyclerView has no idea what kind of data source you'll be using, so it uses what is known as an adapter.
Here in this eg, you'll use an array for the data source.

//Adapter
The RecyclerView adapter will be the middleman.
The RecyclerView wil ask questions to the adapter, such as, how many rows will I need to display? The question is then
delegated to the data source.
Adapter must also provide a ViewHolder. A ViewHolder represents one row in your RecyclerView.
The adapter needs to know the ViewHolder because, remember, the adapter creates new ViewHolders and passes them
to the RecyclerView.
At some point in time when the RecyclerView needs to display a new row, it will provide the adapter with a
ViewHolder and ask the adapter to configure it. This could be a fresh new ViewHolder or one that has already
been used, it doesn't matter. It's just the adapter's job to update this ViewHolder.

 */
class MainActivity : AppCompatActivity() {

    /*
    you'll be accessing your RecyclerView from your activity, so you'll need to add a property
    lateinit keyword just indicates that a RecyclerView is going to be created sometime in the
    future as opposed to when the activity is created.

    If you don't add the lateinit keyword, you would need to initialize the value right away, which
    is a problem, since your activity takes a little time to be generated.
     */

    private lateinit var todoListRecyclerView : RecyclerView

    val listDataManager : ListDataManager = ListDataManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Before you create your recycler view, add the following
        val lists = listDataManager.readLists()

        //get a reference to your RecyclerView
        todoListRecyclerView = findViewById(R.id.lists_recyclerview)

        //The RecyclerView needs to know about your layout when placing items.
        todoListRecyclerView.layoutManager = LinearLayoutManager(this)

        //you need to assign your adapter to your RecyclerView
        //todoListRecyclerView.adapter = TodoListAdapter()

        todoListRecyclerView.adapter = TodoListAdapter(lists)

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*
    Android provides a dialog class with three implementations: An alert dialog,
    a date picker dialog and a time picker dialog.

    For you app you'll use an alert dialog. This allows you to show title, provides up to
    three buttons or a list of selectable items or your own custom layout.

    To get started you'll create a dialog to ask the user to name their to-do list.
     */

    private fun showCreateTodoListDialog(){
        //create some variables to hold some strings
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)
        val myDialog = AlertDialog.Builder(this)

        // next you need an EditText()
        val todoTitleEditText = EditText(this)

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

            val adapter = todoListRecyclerView.adapter as TodoListAdapter

            //First you need to create an empty task list passing in the edit text as title
            val list = TaskList(todoTitleEditText.text.toString())

            //saving it
            listDataManager.saveList(list)

            //now you need to update your recycler view with the list.
            adapter.addList(list)

          //  adapter.addNewItem(todoTitleEditText.text.toString())

            //and once they tap this, you wanna dismiss the dialog
                dialog.dismiss()

        }

        //create and show the dialog
        myDialog.create().show()

    }
}

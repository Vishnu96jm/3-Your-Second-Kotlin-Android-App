package com.raywenderlich.listmaker

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
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

    lateinit var todoListRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //get a reference to your RecyclerView
        todoListRecyclerView = findViewById(R.id.lists_recyclerview)

        //The RecyclerView needs to know about your layout when placing items.
        todoListRecyclerView.layoutManager = LinearLayoutManager(this)

        //you need to assign your adapter to your RecyclerView
        todoListRecyclerView.adapter = TodoListAdapter()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
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

    fun myTestMethod() {
        println("Hello")
    }
}

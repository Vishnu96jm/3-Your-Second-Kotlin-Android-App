package com.raywenderlich.listmaker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TaskDetailFragment : Fragment() {

    lateinit var list: TaskList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //check to see if there are any arguments
        arguments?.let {
            list = it.getParcelable(ARG_LIST)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_detail, container, false)
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
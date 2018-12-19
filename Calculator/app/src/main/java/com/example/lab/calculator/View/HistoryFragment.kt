package com.example.lab.calculator.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab.calculator.R
import com.example.lab.calculator.View.Adapter.HistoryAdapter
import com.example.lab.calculator.ViewModel.CalcViewModel

class HistoryFragment : Fragment(){

    companion object {
        fun getInstance() = HistoryFragment()
    }

    lateinit var historyViewModel: CalcViewModel
    lateinit var resView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        historyViewModel = ViewModelProviders.of(activity!!).get(CalcViewModel::class.java)
        historyViewModel.getHistory().observe(this, Observer {
            refreshUI(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val v = inflater.inflate(R.layout.fragment_history, container, false)
        resView = v.findViewById(R.id.resView)

        //для того чтобы можно было удалять элементы списка (при перетягивании элемента влево)
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                historyViewModel.deleteHistory(viewHolder.adapterPosition!!)
                resView.adapter?.notifyItemRemoved(viewHolder.adapterPosition!!)
            }
        }).attachToRecyclerView(resView)

        return v
    }

    fun refreshUI(arr: ArrayList<String>){
        resView.layoutManager = LinearLayoutManager(activity)
        val historyAdapter = HistoryAdapter(arr)
        resView.adapter = historyAdapter
    }
}

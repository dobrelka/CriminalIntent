package com.example.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.zip.Inflater

private const val TAG = "CrimeListFragment"
    // CrimeListFragment is an empty shell of a fragment. It logs the number of crimes found in CrimeListViewModel
class CrimeListFragment : Fragment() {

    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = null

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
    }

    // Setting up the view for CrimeListFragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        crimeRecyclerView =
            view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }
    // UI  it will create a CrimeAdapter and set it on
    //the RecyclerView
     private fun updateUI() {
         val crimes = crimeListViewModel.crimes
         adapter = CrimeAdapter(crimes)
         crimeRecyclerView.adapter = adapter
     }

     //  The beginnings of a ViewHolder
     private inner class CrimeHolder(view: View)
         : RecyclerView.ViewHolder(view), View.OnClickListener {

         private lateinit var crime: Crime

     //  Pulling out views in the constructor
         private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
         private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)

         init {
             itemView.setOnClickListener(this)
         }

     // Writing a bind(Crime) function
         fun bind(crime: Crime) {
             this.crime = crime
             titleTextView.text = this.crime.title
             dateTextView.text = this.crime.date.toString()
         }

     //  Modify the CrimeHolder to handle presses for the entire row
         override fun onClick(v: View) {
             Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT)
                     .show()
         }

     }
    // An adapter is a controller object that sits between the RecyclerView and the data set that
    //the RecyclerView should display.
     private  inner  class CrimeAdapter(var crimes: List<Crime>)
         : RecyclerView.Adapter<CrimeHolder>() {

         override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                 : CrimeHolder {
             val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
             return CrimeHolder(view)

         }

         override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
             val crime = crimes[position]

             holder.bind(crime)
         }

        override fun getItemCount() = crimes.size
     }

    companion object {
        fun newInstance(): CrimeListFragment {
            return  CrimeListFragment()
        }
    }
}
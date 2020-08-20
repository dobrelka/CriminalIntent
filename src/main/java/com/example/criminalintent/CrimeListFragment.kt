package com.example.criminalintent

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import java.util.zip.Inflater
import javax.security.auth.callback.Callback

private const val TAG = "CrimeListFragment"
    // CrimeListFragment is an empty shell of a fragment. It logs the number of crimes found in CrimeListViewModel
class CrimeListFragment : Fragment() {

    /**
     * Required interface for hosting activities
     */
    interface Callbacks {
        fun onCrimeSelected(crimeId: UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var crimeRecyclerView: RecyclerView
    // Initialize the recycler view adapter with an empty crime list to start.
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    // Define CrimeListFragment.onCreate(Bundle?) and let the FragmentManager know that
        //CrimeListFragment needs to receive menu callbacks
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        crimeRecyclerView.adapter = adapter

        return view
    }

    // The LiveData.observe(LifecycleOwner, Observer) function is used to register an observer on
    //the LiveData instance and tie the life of the observation to the life of another component
    //The second parameter to the observe(...) function is an Observer implementation,
    //responsible for reacting to new data from the LiveData
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let {
                    Log.i(TAG, "Got crimes ${crimes.size}")
                    updateUI(crimes)
                }
        } )
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    // Within this function, you call MenuInflater.inflate(Int, Menu) and pass in the resource ID of your
    //menu file
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list, menu)
    }

    private fun updateUI(crimes: List<Crime>) {

        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
     }

     // The beginnings of a ViewHolder
    private inner class CrimeHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var crime: Crime

     // Pulling out views in the constructor
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        init {
            itemView.setOnClickListener(this)
        }

     // Writing a bind(Crime) function
        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.date.toString()
            solvedImageView.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

     //  Modify the CrimeHolder to handle presses for the entire row
         override fun onClick(v: View) {
            callbacks?.onCrimeSelected(crime.id)
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
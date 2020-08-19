package com.example.criminalintent

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

private const val ARG_DATE = "date"

class DatePickerFragment : DialogFragment() {

    interface Callbacks {
        fun onDateSelected(date: Date)
    }

    // The OnDateSetListener is used to receive the date the user selects
    // The selected date is provided in year, month, and day format, but you need a Date to send back to
    //CrimeFragment . You pass these values to the GregorianCalendar and access the time property to get a Date object
    // Once you have the date, it needs to be sent back to CrimeFragment . The targetFragment property
    //stores the fragment instance that started your DatePickerFragment . Since it is nullable, you wrap it
    //in a safe-call let block. You then cast the fragment instance to your Callbacks interface and call the
    //onDateSelected() function, passing in your new date.
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dateListener = DatePickerDialog.OnDateSetListener {
                _: DatePicker, year: Int, month: Int, day: Int ->

            val resultDate : Date = GregorianCalendar(year, month, day).time

            targetFragment?.let { fragment ->
                (fragment as Callbacks).onDateSelected(resultDate)
            }
        }

        // Now CrimeFragment is successfully telling DatePickerFragment what date to show
        val date = arguments?.getSerializable(ARG_DATE) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(),
            dateListener,
            initialYear,
            initialMonth,
            initialDay
        )
    }

    companion object {
        fun newInstance(date: Date): DatePickerFragment {
            val args = Bundle(). apply {
                putSerializable(ARG_DATE, date)
            }
            return DatePickerFragment().apply {
                arguments = args
            }
        }
    }
}
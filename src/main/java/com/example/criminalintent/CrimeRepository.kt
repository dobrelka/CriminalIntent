package com.example.criminalintent

import android.content.Context
import java.lang.IllegalStateException

class CrimeRepository private constructor(context: Context) {

    // To make CrimeRepository a singleton, you add two functions to its companion object. One initializes
    //a new instance of the repository, and the other accesses the repository.
    companion object {
        private  var INSTANCE: CrimeRepository? = null

        fun initialize (context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}
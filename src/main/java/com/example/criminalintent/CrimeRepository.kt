package com.example.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.criminalintent.database.CrimeDatabase
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {

    // Room.databaseBuilder() creates a concrete implementation of your abstract CrimeDatabase using
    //three parameters
    private val database : CrimeDatabase = Room.databaseBuilder(
            context.applicationContext,
            CrimeDatabase::class.java,
            DATABASE_NAME
    ).build()

    private val crimeDao = database.crimeDao()
    // Create an executor that uses a new thread (which will always be a background thread).
    private val executor = Executors.newSingleThreadExecutor()

    // Updated CrimeRepository to return LiveData from its query functions
    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()

    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)

    fun updateCrime(crime: Crime) {
        executor.execute {
            crimeDao.updateCrime(crime)
        }
    }

    fun addCrime(crime: Crime) {
        executor.execute {
            crimeDao.addCrime(crime)
        }
    }

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
package com.example.criminalintent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.criminalintent.Crime
import java.util.*

// The @Dao annotation lets Room know that CrimeDao
// is one of your data access objects
@Dao
interface CrimeDao {

    // Adding database query functions
    // Asks Room to pull all columns for all rows in the crime database
    // By returning an instance of LiveData from your DAO class, you signal Room to run your query on a
    //background thread
    @Query("SELECT * FROM crime ")
    fun getCrimes(): LiveData<List<Crime>>

    // Asks Room to pull all columns from only the row whose id matches
    //the ID value provided
    @Query("SELECT * FROM crime WHERE id=(:id)")
    fun getCrime(id: UUID): LiveData<Crime?>
}
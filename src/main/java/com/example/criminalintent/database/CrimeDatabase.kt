package com.example.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.criminalintent.Crime

// The first parameter is a list of entity classes
@Database(entities = [ Crime::class ], version = 1)
// Enabling TypeConverters you tell your database to use the functions in that class
@TypeConverters(CrimeTypeConverters::class)
// The second parameter is the version of the database
abstract class CrimeDatabase : RoomDatabase() {

    // Registering the DAO in the database
    abstract fun crimeDao(): CrimeDao

}
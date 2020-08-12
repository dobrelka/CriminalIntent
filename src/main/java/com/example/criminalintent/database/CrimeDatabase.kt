package com.example.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.criminalintent.Crime

// The first parameter is a list of entity classes
@Database(entities = [ Crime::class ], version = 1)
// The second parameter is the version of the database
abstract class CrimeDatabase : RoomDatabase() {

}
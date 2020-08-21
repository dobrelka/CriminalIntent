package com.example.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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

// Since your initial database version is set to 1, you bump it up to 2. You then create a Migration object
//that contains instructions for updating your database.
//The Migration class constructor takes in two parameters. The first is the database version you are
//migrating from, and the second is the version you are migrating to. In this case, you provide the
//version numbers 1 and 2
//
//The only function you need to implement in your Migration object is
//migrate(SupportSQLiteDatabase)
//
//You use the database parameter to execute any SQL commands necessary to upgrade your tables
val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
           "ALTER TABLE Crime ADD COLUMN suspect TEXT NOT NULL DEFAULT ''"
        )
    }
}
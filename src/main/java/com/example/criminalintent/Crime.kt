package com.example.criminalintent

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.Math.random
import java.util.*

// Column table with four values
@Entity
data class Crime(@PrimaryKey val id: UUID = UUID.randomUUID(),
                 var title: String = "",
                 var date: Date = Date(),
                 var isSolved:Boolean = false)

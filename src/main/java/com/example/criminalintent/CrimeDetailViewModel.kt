package com.example.criminalintent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.io.File
import java.util.*

class CrimeDetailViewModel() : ViewModel() {

    private val crimeRepository = CrimeRepository.get()
    private val crimeIdLiveData = MutableLiveData<UUID>()

    var crimeLiveData: LiveData<Crime?> =
        Transformations.switchMap(crimeIdLiveData) { crimeId ->
            crimeRepository.getCrime(crimeId)
        }

    // When CrimeFragment requests to load a crime with a given ID, its CrimeDetailViewModel should kick
    //off a getCrime(UUID) database request
    fun loadCrime(crimeId: UUID) {
        crimeIdLiveData.value = crimeId
    }

    //SaveCrime(Crime) accepts a Crime and writes it to the database
    fun saveCrime(crime: Crime) {
        crimeRepository.updateCrime(crime)
    }

    fun getPhotoFile(crime: Crime): File {
        return  crimeRepository.getPhotoFile(crime)
    }
}
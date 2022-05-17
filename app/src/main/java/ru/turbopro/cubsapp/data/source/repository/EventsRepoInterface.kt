package ru.turbopro.cubsapp.data.source.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import ru.turbopro.cubsapp.data.Event
import ru.turbopro.cubsapp.data.Result
import ru.turbopro.cubsapp.data.utils.StoreEventDataStatus

interface EventsRepoInterface {
    suspend fun refreshEvents(): StoreEventDataStatus?
    fun observeEvents(): LiveData<Result<List<Event>>?>
    suspend fun getEventById(eventId: String, forceUpdate: Boolean = false): Result<Event>
    suspend fun getEventByDate(eventDate: String, forceUpdate: Boolean = false): Result<Event>
    //suspend fun getUpcomingEvent(): Result<String>
    suspend fun insertEvent(newEvent: Event): Result<Boolean>
    suspend fun insertImages(imgList: List<Uri>): List<String>
    suspend fun updateEvent(event: Event): Result<Boolean>
    suspend fun updateImages(newList: List<Uri>, oldList: List<String>): List<String>
    suspend fun deleteEventById(eventId: String): Result<Boolean>
}
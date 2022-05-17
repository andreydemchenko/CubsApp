package ru.turbopro.cubsapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.turbopro.cubsapp.CubsApplication
import ru.turbopro.cubsapp.data.Result
import ru.turbopro.cubsapp.data.CubsAppSessionManager
import ru.turbopro.cubsapp.data.UserData

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepository =
        (application.applicationContext as CubsApplication).authRepository

    private val sessionManager = CubsAppSessionManager(application.applicationContext)
    private val currentUser = sessionManager.getUserIdFromSession()
    val isUserASeller = sessionManager.isUserSeller()

    private val _userData = MutableLiveData<UserData?>()
    val userData: LiveData<UserData?> get() = _userData

    init {
        viewModelScope.launch {
            authRepository.hardRefreshUserData()
        }
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val res = async { authRepository.refreshData() }
            res.await()
        }
    }

    //fun getUpcomingEvent() = sessionManager.getUserDataFromSession()

    fun getUserData() {
        viewModelScope.launch {
            val deferredRes = async { authRepository.getUserData(currentUser!!) }
            val res = deferredRes.await()
            if (res is Result.Success) {
                val uData = res.data
                _userData.value = uData
            } else {
                _userData.value = null
            }
        }
    }
}
package ru.turbopro.cubsapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.turbopro.cubsapp.CubsApplication
import ru.turbopro.cubsapp.data.CubsAppSessionManager

private const val TAG = "AchievementViewModel"

class AchievementViewModel(private val achievementId: String, application: Application) :
    AndroidViewModel(application) {



    private val achievementsRepository = (application as CubsApplication).achievementsRepository
    private val authRepository = (application as CubsApplication).authRepository
    private val sessionManager = CubsAppSessionManager(application.applicationContext)
    private val currentUserId = sessionManager.getUserIdFromSession()
}
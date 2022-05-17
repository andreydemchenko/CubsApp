package ru.turbopro.cubsapp.data.source

import android.net.Uri
import androidx.lifecycle.LiveData
import ru.turbopro.cubsapp.data.Achievement
import ru.turbopro.cubsapp.data.Result

interface AchievementDataSource {

    fun observeAchievements(): LiveData<Result<List<Achievement>>?>

    suspend fun getAllAchievements(): Result<List<Achievement>>

    suspend fun refreshAchievements() {}

    suspend fun getAchievementById(achievementId: String): Result<Achievement>

    suspend fun insertAchievement(newAchievement: Achievement)

    suspend fun updateAchievement(achData: Achievement)

    suspend fun uploadImage(uri: Uri, fileName: String): Uri? {
        return null
    }

    fun revertUpload(fileName: String) {}
    fun deleteImage(imgUrl: String) {}
    suspend fun deleteAchievement(achievementId: String)
    suspend fun deleteAllAchievements() {}
    suspend fun insertMultipleAchievements(data: List<Achievement>) {}
}
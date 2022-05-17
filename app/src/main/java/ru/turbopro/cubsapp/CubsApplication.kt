package ru.turbopro.cubsapp

import android.app.Application
import ru.turbopro.cubsapp.data.source.repository.AchievementsRepoInterface
import ru.turbopro.cubsapp.data.source.repository.AuthRepoInterface
import ru.turbopro.cubsapp.data.source.repository.EventsRepoInterface
import ru.turbopro.cubsapp.data.source.repository.ProductsRepoInterface

class CubsApplication : Application() {
	val authRepository: AuthRepoInterface
		get() = ServiceLocator.provideAuthRepository(this)

	val productsRepository: ProductsRepoInterface
		get() = ServiceLocator.provideProductsRepository(this)

	val eventsRepository: EventsRepoInterface
		get() = ServiceLocator.provideEventsRepository(this)

	val achievementsRepository: AchievementsRepoInterface
		get() = ServiceLocator.provideAchievementsRepository(this)

	override fun onCreate() {
		super.onCreate()
	}
}
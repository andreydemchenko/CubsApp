package ru.turbopro.cubsapp.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.turbopro.cubsapp.data.Product
import ru.turbopro.cubsapp.data.UserData
import ru.turbopro.cubsapp.data.Achievement
import ru.turbopro.cubsapp.data.Event
import ru.turbopro.cubsapp.data.utils.DateTypeConvertors
import ru.turbopro.cubsapp.data.utils.ListTypeConverter
import ru.turbopro.cubsapp.data.utils.ObjectListTypeConvertor

@Database(entities = [UserData::class, Product::class, Achievement::class, Event::class], version = 1)
@TypeConverters(ListTypeConverter::class, ObjectListTypeConvertor::class, DateTypeConvertors::class)
abstract class ShoppingAppDatabase : RoomDatabase() {
	abstract fun userDao(): UserDao
	abstract fun productsDao(): ProductsDao
	abstract fun achievementsDao(): AchievementsDao
	abstract fun eventsDao(): EventsDao

	companion object {
		@Volatile
		private var INSTANCE: ShoppingAppDatabase? = null

		fun getInstance(context: Context): ShoppingAppDatabase =
			INSTANCE ?: synchronized(this) {
				INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
			}

		private fun buildDatabase(context: Context) =
			Room.databaseBuilder(
				context.applicationContext,
				ShoppingAppDatabase::class.java, "ShoppingAppDb"
			)
				.fallbackToDestructiveMigration()
				.allowMainThreadQueries()
				.build()
	}
}
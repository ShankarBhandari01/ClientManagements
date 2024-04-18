package com.shankar.clientmanagements.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shankar.clientmanagements.dao.OfferResponseDAO
import com.shankar.clientmanagements.dao.UserResponseDAO
import com.shankar.clientmanagements.entity.Clients
import com.shankar.clientmanagements.entity.allpackage

@Database(
    entities = [(Clients::class),(allpackage::class)],
    version = 1,
    exportSchema = false
)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun getUserResponseDAO(): UserResponseDAO
    abstract fun GetOfferResponseDAO():OfferResponseDAO

    companion object {
        @Volatile
        private var instance: RoomDataBase? = null
        fun getInstance(context: Context): RoomDataBase {
            if (instance == null) {
                synchronized(RoomDataBase::class) {
                    instance = buildDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RoomDataBase::class.java,
                "RoomDatabase"
            ).build()


    }
}
package id.phephen.roomcrud.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.phephen.roomcrud.model.User

/**
 * Created by phephen on 07,February,2021
 * https://github.com/fendysaputro
 */

@Database(entities = [User::class], version = 1)
abstract class UserDB : RoomDatabase(){

    abstract fun userDao() : UserDao

    companion object {

        @Volatile private var instance : UserDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                UserDB::class.java,
                "user_database.db"
        ).build()

    }
}
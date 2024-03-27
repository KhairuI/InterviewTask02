package com.example.interviewtask02.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.interviewtask02.model.ModelUser

@Database(entities = [ModelUser::class],version = 1)
abstract class UserDatabase:RoomDatabase() {

    abstract val userDao: UserDAO

    companion object{

        @Volatile
        private var instance: UserDatabase?= null

        fun getInstance(context: Context): UserDatabase {
            synchronized(this){
                return  instance ?: Room.databaseBuilder(context.applicationContext,
                    UserDatabase::class.java,"UserDB").fallbackToDestructiveMigration().allowMainThreadQueries().build()
                    .also {
                        instance = it
                    }

            }
        }

    }

}
package com.example.interviewtask02.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.interviewtask02.model.ModelUser

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: ModelUser): Long

    @Transaction
    @Query("SELECT * FROM user_table")
    fun getAllUser(): MutableList<ModelUser>

    @Transaction
    @Query("DELETE FROM user_table WHERE id = :id")
    fun deleteUser(id: Int): Int

}
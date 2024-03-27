package com.example.interviewtask02.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user_table")
data class ModelUser(

    @ColumnInfo(name="name")
    val name:String?,

    @ColumnInfo(name="address")
    val address:String?,

    ):Serializable{
    @PrimaryKey(autoGenerate = true)
    var id:Int?= null

}
package com.example.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Note(@PrimaryKey(autoGenerate = true)
                val id : Int= 0,
                val titel: String,
                val time: String,
                val date: String,)
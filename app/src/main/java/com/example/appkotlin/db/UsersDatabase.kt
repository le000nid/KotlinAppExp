package com.example.appkotlin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appkotlin.Users

@Database(entities = [(Users::class)], version = 1)
abstract class UsersDatabase : RoomDatabase(){

    abstract fun usersDao() : UsersDao
}
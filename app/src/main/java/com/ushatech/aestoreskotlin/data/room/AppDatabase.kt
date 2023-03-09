package com.ushatech.aestoreskotlin.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ushatech.aestoreskotlin.data.tables.UserCartTable

@Database(entities = arrayOf(UserCartTable::class), version = 2)
public abstract class AppDatabase : RoomDatabase(){

    // Make that much abstract function = number of interfaces.
    // One table= One New Dao Interface.

    abstract fun userCartDao() : UserCartDao


    // Need to Study about the below code and What does this code actually do always.
    //  Need R & D on this.

//    companion object {
//        @Volatile private var instance: AppDatabase? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
//            instance ?: buildDatabase(context).also { instance = it}
//        }
//
//        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
//            AppDatabase::class.java, "todo-list.db")
//            .build()
//    }


}
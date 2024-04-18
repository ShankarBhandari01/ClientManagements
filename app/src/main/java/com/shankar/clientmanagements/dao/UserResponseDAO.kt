package com.shankar.clientmanagements.dao

import androidx.room.*
import com.shankar.clientmanagements.entity.Clients

@Dao
interface UserResponseDAO {
    @Query("DELETE FROM User_Table")
    fun deleteTables(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(Clients: MutableList<Clients>)

    @Query("select*from User_Table")
    suspend fun getClients():MutableList<Clients>

    @Update
    suspend fun updateData(clients: Clients)

}

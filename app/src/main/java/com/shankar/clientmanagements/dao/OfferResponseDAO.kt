package com.shankar.clientmanagements.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shankar.clientmanagements.entity.allpackage
@Dao
interface OfferResponseDAO {
    @Query("DELETE FROM Offer_Table")
    fun deleteTables(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(Offers: MutableList<allpackage>)

    @Query("select*from Offer_Table")
    suspend fun getAllOffers():MutableList<allpackage>
}
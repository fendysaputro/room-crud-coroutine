package id.phephen.roomcrud.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import id.phephen.roomcrud.model.User

/**
 * Created by phephen on 07,February,2021
 * https://github.com/fendysaputro
 */

@Dao
interface UserDao {

    @Insert
    suspend fun addUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user ORDER BY id DESC")
    suspend fun getUsers(): List<User>
}
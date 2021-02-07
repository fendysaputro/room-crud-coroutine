package id.phephen.roomcrud.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by phephen on 07,February,2021
 * https://github.com/fendysaputro
 */

@Entity(tableName = "user")
data class User(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "date")
    val date: String
)

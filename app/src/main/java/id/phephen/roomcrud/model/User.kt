package id.phephen.roomcrud.model

import android.os.Parcelable
import android.provider.ContactsContract
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Created by phephen on 07,February,2021
 * https://github.com/fendysaputro
 */

@Entity(tableName = "user")

@Parcelize
data class User(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "phone")
    val phone: String
) : Parcelable

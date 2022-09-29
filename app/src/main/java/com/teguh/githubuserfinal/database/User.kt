package com.teguh.githubuserfinal.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_table")
@Parcelize
data class User (
    @ColumnInfo(name = "github_id")
    @PrimaryKey
    var githubId: String,

    @ColumnInfo(name = "github_login")
    var githubLogin: String? = null,

    @ColumnInfo(name = "github_name")
    var githubName: String? = null,

    @ColumnInfo(name = "github_avatar_url")
    var githubAvatarUrl: String? = null,

    @ColumnInfo(name = "date")
    var date: String? = null,
) : Parcelable

package core

import java.sql.Timestamp

data class User(val id: Long, var points: Int, var lastPointTimeStamp: Long)
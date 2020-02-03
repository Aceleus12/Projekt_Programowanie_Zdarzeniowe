package com.udemy.chodkowski

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Contact (@PrimaryKey val id: UUID = UUID.randomUUID(),
                    var name: String = "",
                    var surname: String = "",
                    var city: String = "",
                    var street: String = "",
                    var number: String ="")
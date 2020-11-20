package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(val id : String,
                var firstName : String?,
                var lastName : String?,
                var avatar : String?,
                var rating : Int = 0,
                var respect : Int = 0,
                var lastVisit : Date? = Date(),
                var isOnline : Boolean = false) {

    constructor(id : String,
                firstName : String?,
                lastName : String?): this(id, firstName, lastName, null)

    class Builder {
        private var id : String? = null
        private var firstName : String? = null
        private var lastName : String? = null
        private var avatar : String? = null
        private var rating : Int = 0
        private var respect : Int = 0
        private var lastVisit : Date? = Date()
        private var isOnline : Boolean = false

        fun id(id:String) = this.also { this.id = id }
        fun firstName(firstName:String?) = this.also { this.firstName = firstName }
        fun lastName(lastName:String?) = this.also { this.lastName = lastName }
        fun avatar(avatar:String?) = this.also { this.avatar = avatar }
        fun rating(rating:Int) = this.also { this.rating = rating }
        fun respect(respect:Int) = this.also { this.respect = respect }
        fun lastVisit(lastVisit:Date?) = this.also { this.respect = respect }
        fun isOnline(isOnline:Boolean) = this.also { this.isOnline = isOnline }

        fun build() = if(id == null) null else User(
            id!!,
            firstName,
            lastName,
            avatar,
            rating,
            respect,
            lastVisit,
            isOnline
        )

    }

    companion object Factory {
        private var newId = -1
        get() {
            field++
            return field
        }
        fun makeUser(fullName: String?): User {
            val (firstname, lastname) = Utils.parseFullName(fullName)
            return User(newId.toString(),
                        firstName = firstname,
                        lastName = lastname)
        }
    }
}
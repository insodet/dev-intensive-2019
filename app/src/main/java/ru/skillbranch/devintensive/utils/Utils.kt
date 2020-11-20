package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullname:String?):Pair<String?, String?>{
        val trimmedName = fullname?.trim()
        val parts = if (trimmedName.isNullOrEmpty()) null else trimmedName.split(" ")

        val firstname = parts?.getOrNull(0)
        val lastname = parts?.getOrNull(1)

        return Pair(firstname, lastname)
    }

    fun toInitials(firstName:String?, lastName:String?): String? {
        val getInitial:(String?) -> Char? = {
            if(it.isNullOrBlank()) null else it.trim().first().toUpperCase()
        }

        val first = getInitial(firstName)
        val second = getInitial(lastName)

        return if (first == null && second == null) null else "${first ?: ""}${second ?: ""}"
    }

    fun transliteration(payload:String?, divider:String = " ") =
        payload?.replace(Regex("[А-Яа-я ]")) {
            val replacement = when(it.value.toLowerCase()){
                " " -> divider
                "а" -> "a"
                "б" -> "b"
                "в" -> "v"
                "г" -> "g"
                "д" -> "d"
                "е", "ё", "э" -> "e"
                "ж" -> "zh"
                "з" -> "z"
                "и", "й", "ы" -> "i"
                "к" -> "k"
                "л" -> "l"
                "м" -> "m"
                "н" -> "n"
                "о" -> "o"
                "п" -> "p"
                "р" -> "r"
                "с" -> "s"
                "т" -> "t"
                "у" -> "u"
                "ф" -> "f"
                "х" -> "h"
                "ц" -> "c"
                "ч" -> "ch"
                "ш" -> "sh"
                "щ" -> "sh'"
                "ъ", "ь" -> ""
                "ю" -> "yu"
                "я" -> "ya"
                else -> it.value
            }
            if (it.value.first().isUpperCase())
                replacement.capitalize()
            else
                replacement
        }
}
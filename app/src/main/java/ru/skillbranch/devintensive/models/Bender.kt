package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion() =
        question.question

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> =
        when {
            !question.validateAnswer(answer) -> "${question.invalidAnswer}\n${question.question}" to status.color
            question.answers.contains(answer.toLowerCase()) || question == Question.IDLE -> {
                question = question.nextQuestion()
                "Отлично - ты справился\n${question.question}" to status.color
            }
            else -> {
                status = status.nextStatus()
                if (status == Status.NORMAL) {
                    question = Question.NAME
                    "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
                } else
                    "Это неправильный ответ\n${question.question}" to status.color
            }
        }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus() =
            if (this.ordinal < values().lastIndex) values()[this.ordinal + 1] else values()[0]
    }

    enum class Question(
        val question: String,
        val answers: List<String>,
        val invalidAnswer: String = ""
    ) {
        NAME(
            "Как меня зовут?",
            listOf("бендер", "bender"),
            "Имя должно начинаться с заглавной буквы"
        ) {
            override fun nextQuestion(): Question = PROFESSION
            override fun validateAnswer(answer: String): Boolean =
                answer.isNotEmpty() && answer[0].isUpperCase()
        },
        PROFESSION(
            "Назови мою профессию?",
            listOf("сгибальщик", "bender"),
            "Профессия должна начинаться со строчной буквы"
        ) {
            override fun nextQuestion(): Question = MATERIAL
            override fun validateAnswer(answer: String): Boolean =
                answer.isNotEmpty() && answer[0].isLowerCase()
        },
        MATERIAL(
            "Из чего я сделан?",
            listOf("металл", "дерево", "metal", "iron", "wood"),
            "Материал не должен содержать цифр"
        ) {
            override fun nextQuestion(): Question = BDAY
            override fun validateAnswer(answer: String): Boolean = !answer.contains(Regex("[0-9]"))
        },
        BDAY(
            "Когда меня создали?",
            listOf("2993"),
            "Год моего рождения должен содержать только цифры"
        ) {
            override fun nextQuestion(): Question = SERIAL
            override fun validateAnswer(answer: String): Boolean =
                !answer.contains(Regex("[^0-9]"))
        },
        SERIAL(
            "Мой серийный номер?",
            listOf("2716057"),
            "Серийный номер содержит только цифры, и их 7"
        ) {
            override fun nextQuestion(): Question = IDLE
            override fun validateAnswer(answer: String): Boolean =
                answer.length == 7 && !answer.contains(Regex("[^0-9]"))
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun validateAnswer(answer: String): Boolean = true
        };

        abstract fun nextQuestion(): Question
        abstract fun validateAnswer(answer: String): Boolean
    }
}
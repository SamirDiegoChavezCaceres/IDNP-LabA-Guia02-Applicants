package com.example.applicant.util

enum class Recurrence {
    Medicina,
    Ingeniería_Civil,
    Psicología,
    Derecho,
    Administración_de_Empresas,
    Ciencias_de_la_Computación,
    Biología,
    Economía,
    Arquitectura,
    Educación,
    Enfermería,
    Ciencias_Políticas,
    Contabilidad,
    Diseño_Gráfico,
    Matemáticas,
    Historia,
    Química,
    Periodismo,
    Sociología,
    Música
}

fun getRecurrenceList(): List<Recurrence> {
    val recurrenceList = mutableListOf<Recurrence>()
    enumValues<Recurrence>().forEach { recurrenceList.add(it) }
    return recurrenceList
}
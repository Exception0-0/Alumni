package dev.than0s.aluminium.features.register

const val student = "Student"
const val staff = "Staff"
const val alumni = "Alumni"

data class Param(
    val category: String = student,
    val id: String = "",
    val email: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val batch: String = "",
)
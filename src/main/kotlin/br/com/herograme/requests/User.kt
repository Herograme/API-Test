package br.com.herograme.requests

import kotlinx.serialization.Serializable


@Serializable
data class User(
    val Id:Int? = null,
    val Nome:String,
    val Email:String
)

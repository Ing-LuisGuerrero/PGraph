package com.equipo1.pgraph.models

data class User(val id: String?, val name: String, val email: String?) {

    constructor(name: String): this(null, name, null)
}

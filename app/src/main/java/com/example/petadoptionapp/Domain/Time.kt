package com.example.petadoptionapp.Domain

class Time {
    var id: Int = 0
    var Value: String = ""

    constructor()

    constructor(id: Int, Value: String) {
        this.id = id
        this.Value = Value
    }

    override fun toString(): String {
        return Value
    }
}

package com.example.petadoptionapp.Domain

class Location {
    var id: Int = 0
    var loc: String = ""

    constructor()

    constructor(id: Int, loc: String) {
        this.id = id
        this.loc = loc
    }

    override fun toString(): String {
        return loc
    }
}

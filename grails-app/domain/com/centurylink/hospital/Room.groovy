package com.centurylink.hospital

class Room {

    long id
    static hasMany = [staff: Nurse, occupier: Patient]

    static constraints = {

        occupier nullable:  true // Representing the circle on the room number entity link to patient
        staff nullable: false // Representing the solid dash on the Nurse link
    }
}

package com.centurylink.hospital

class Nurse {

    long id
    long room_id
    String firstName
    String lastName
    static hasOne = [staffing_room: Room, supervisor: Nurse]
    static hasMany = [supervised: Nurse]

    static constraints = {

        room_id nullable: false
    }
}

package com.centurylink.hospital

class Patient {

    long id
    String firstName
    String lastName
    static hasMany = [received_prescription: Prescription]
    static hasOne = [primary_care_doctor: Doctor, occupied_room: Room]

    static constraints = {
    }
}

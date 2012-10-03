package com.centurylink.hospital

class Prescription {

    long id
    static hasOne = [receiving_patient: Patient, prescribed_drug: Drug, providing_doctor: Doctor]

    static constraints = {
    }
}

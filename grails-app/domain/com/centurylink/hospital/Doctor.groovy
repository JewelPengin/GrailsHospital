package com.centurylink.hospital

class Doctor {

    long id
    String firstName
    String lastName

    static hasMany = [provided_prescription: Prescription, primary_care_patient: Patient]

    static constraints = {

    }
}

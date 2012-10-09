package com.centurylink.hospital

class Prescription {

	Date dateCreated
    Date expirationDate

    static hasOne = [receivingPatient: Patient, prescribedDrug: Drug, providingDoctor: Doctor]

	static constraints = {
        expirationDate datetime: true
    }

}

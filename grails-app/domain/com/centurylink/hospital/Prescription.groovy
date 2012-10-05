package com.centurylink.hospital

class Prescription {

	static hasOne = [receivingPatient: Patient, prescribedDrug: Drug, providingDoctor: Doctor]

	static constraints = {}

}

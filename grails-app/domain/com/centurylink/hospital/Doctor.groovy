package com.centurylink.hospital

class Doctor {

	String firstName
	String lastName
	static hasMany = [writtenPrescription: Prescription, primaryCarePatient: Patient]

	static constraints = {}

}

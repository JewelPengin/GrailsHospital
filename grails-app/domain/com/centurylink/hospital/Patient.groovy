package com.centurylink.hospital

class Patient {

	String firstName
	String lastName
	static hasOne = [primaryCareDoctor: Doctor, occupiedRoom: Room]
	static hasMany = [recievedPrescription: Prescription]

	static constraints = {
		occupiedRoom nullable: true
	}

}

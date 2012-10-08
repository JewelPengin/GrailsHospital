package com.centurylink.hospital

class Room {

	String roomNumber

	static hasMany = [staff: Nurse, occupingPatient: Patient]

	static constraints = {
		occupingPatient nullable: true // Representing the circle on the room number entity link to patient
		staff nullable: true // We need to be able to add rooms without staff, add programmatic constraints
		//roomNumber nullable: true //testing something
	}

}

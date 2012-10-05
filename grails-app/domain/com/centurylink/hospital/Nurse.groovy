package com.centurylink.hospital

class Nurse {

	String firstName
	String lastName
	static hasOne = [staffedRoom: Room, supervisor: Nurse]
	static hasMany = [supervisedNurse: Nurse]

	static constraints = {
		supervisor nullable: true // A nurse doesn't have to have a supervisor
	}

}

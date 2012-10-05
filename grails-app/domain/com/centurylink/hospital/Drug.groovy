package com.centurylink.hospital

class Drug {

	String drugName

	static hasMany = [containingPrescription: Prescription]

	static constraints = {}

}

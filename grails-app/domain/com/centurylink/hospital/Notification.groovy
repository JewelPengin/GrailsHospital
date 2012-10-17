package com.centurylink.hospital

class Notification {
	String message
	String link
	String type
	Date dateCreated

	static hasOne = [person: auth.Person]

	static constraints = {
		person nullable: true
		link blank: true
		message blank: true
	}
}

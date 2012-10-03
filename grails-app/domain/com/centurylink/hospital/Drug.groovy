package com.centurylink.hospital

class Drug {

    long id
    String drugName

    static hasMany = [contained_prescription: Prescription]
    static constraints = {


    }
}

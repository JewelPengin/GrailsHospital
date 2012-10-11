package com.centurylink.hospital

class UserSetting {

    String key
    String value
    static hasOne = [person: auth.Person]

}

package com.centurylink.hospital

class UserService {

    def springSecurityService

    def saveGridParamaters(key, value) {

        if (springSecurityService.isLoggedIn()) {

            key = 'Grid_' + key
            def setting = UserSetting.findByKey(key) ?: new UserSetting()

            setting.key = key
            setting.value = value
            setting.setPerson(springSecurityService.currentUser)

            setting.save(flush: true)

        } else {
            throw new Exception('You are not logged in.')
        }

        return true

    }
}

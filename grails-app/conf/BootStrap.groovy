import com.centurylink.hospital.auth.Authority
import com.centurylink.hospital.auth.Person
import com.centurylink.hospital.auth.PersonAuthority

class BootStrap {

    def springSecurityService

    def init = { servletContext ->
        createData()
    }
    def destroy = {
    }

    public void createData(){
        def userRole = Authority.findByAuthority('ROLE_USER') ?: new Authority(authority: 'ROLE_USER').save(flush:true)
        def adminRole = Authority.findByAuthority('ROLE_ADMIN')?: new Authority(authority: 'ROLE_ADMIN').save(flush: true)
        [
            dave: [name: 'Dave Gunawan', role: userRole]
            , jonathan: [name: 'Jonathan Larson', role: adminRole]
            , brad: [name: 'Bradley Rhoades', role: userRole]
            , farouk:[name:'Farouk Althlathini', role: adminRole]
        ].each{ username, userData ->
            def userParams = [
                username: username
                , enabled: true
                , realname: userData.name
                , password: '1234'
            ]
            def user = Person.findByUsername(username)?: new Person(userParams).save(flush: true)

            if (!user.authorities.contains(userData.role)){
                PersonAuthority.create user, userData.role, true
            }
        }

        assert Person.count() == 4
        assert Authority.count() == 2
        assert PersonAuthority.count() == 4
    }

/*            if (!user.validate()) {
    user.errors.allErrors.each {
        println it
    }
} else {
}*/

}

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

        def user = Person.findByUsername('farouk') ?: new Person(username:  'farouk', enabled: true, password: '1234').save(flush:true)
        def user2 = Person.findByUsername('dave') ?: new Person(username: 'dave',enabled: true, password: 'dave').save(flush: true)

        if (!user.authorities.contains(userRole)){
        PersonAuthority.create user, userRole, true
        }

        if (!user2.authorities.contains(userRole)){
            PersonAuthority.create user2, userRole, true
        }
        assert Person.count() == 2
        assert Authority.count() == 1
        assert PersonAuthority.count() == 2
    }
}

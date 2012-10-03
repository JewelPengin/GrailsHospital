import com.centurylink.hospital.auth.Authority
import com.centurylink.hospital.auth.Person
import com.centurylink.hospital.auth.PersonAuthority

class BootStrap {

    def springSecurityService

    def init = { servletContext ->
        if(!Person.count()){
            createData()
        }
    }
    def destroy = {
    }

    public void createData(){
        def userRole = new Authority(authority: 'ROLE_USER').save(failOnerror: true)
        def password = springSecurityService.encodePassword('password')

        def user = new Person(username:  'farouk', realname: 'farouk',password: password).save(failOnerror: true)
        PersonAuthority.create user, userRole, true
    }
}

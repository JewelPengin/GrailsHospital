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

	public void createData() {
		def userRole = Authority.findByAuthority('ROLE_USER') ?: new Authority(authority: 'ROLE_USER').save(flush:true)
		def adminRole = Authority.findByAuthority('ROLE_ADMIN') ?: new Authority(authority: 'ROLE_ADMIN').save(flush:true)

		def farouk = Person.findByUsername('farouk') ?: new Person(username:  'farouk', enabled: true, password: '1234').save(flush:true)
		def dave = Person.findByUsername('dave') ?: new Person(username: 'dave', enabled: true, password: 'dave').save(flush: true)
		def jonathan = Person.findByUsername('jonathan') ?: new Person(username: 'jonathan', enabled: true, password: 'jwl').save(flush: true)
		def user = Person.findByUsername('user') ?: new Person(username: 'user', enabled: true, password: 'user').save(flush: true)
		def admin = Person.findByUsername('admin') ?: new Person(username: 'admin', enabled: true, password: 'admin').save(flush: true)

		if (!farouk.authorities.contains(adminRole)) {
			PersonAuthority.create farouk, adminRole, true
		}

		if (!jonathan.authorities.contains(adminRole)) {
			PersonAuthority.create jonathan, adminRole, true
		}

		if (!dave.authorities.contains(adminRole)) {
			PersonAuthority.create dave, adminRole, true
		}

		if (!user.authorities.contains(adminRole)) {
			PersonAuthority.create user, userRole, true
		}

		if (!admin.authorities.contains(adminRole)) {
			PersonAuthority.create admin, adminRole, true
		}


		assert Person.count() == 5
		assert Authority.count() == 2
		assert PersonAuthority.count() == 5
	}
}

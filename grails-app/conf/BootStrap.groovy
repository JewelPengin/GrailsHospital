import com.centurylink.hospital.auth.Authority
import com.centurylink.hospital.auth.Person
import com.centurylink.hospital.auth.PersonAuthority
import com.centurylink.hospital.Doctor
import com.centurylink.hospital.Drug
import com.centurylink.hospital.Room

class BootStrap {

	def springSecurityService

	def init = { servletContext ->
		createData()
	}
    public void createData(){

        //login data
        def userRole = Authority.findByAuthority('ROLE_USER') ?: new Authority(authority: 'ROLE_USER').save(flush:true)
        def adminRole = Authority.findByAuthority('ROLE_ADMIN')?: new Authority(authority: 'ROLE_ADMIN').save(flush: true)

        [dave: [name: 'Dave Gunawan', role: userRole]
                , jonathan: [name: 'Jonathan Larson', role: adminRole]
                , brad: [name: 'Bradley Rhoades', role: userRole]
                , farouk: [name:'Farouk Althlathini', role: adminRole]
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

        //create some hospital data

        [Damon: 'Matt', Pitt: 'Brad', Hopkins: 'Anthony', Gere: 'Richard', Clooney: 'George']
                .each {lastname, firstname ->
            def cardiologist = Doctor.findByLastName(lastname)?:
                new Doctor(firstName: firstname, lastName: lastname).save(flush:true)}
        ['advil', 'ibuprofen', 'nyquil', 'neosporin', 'centrum', 'fishoil'].each {drug ->
            def druglist = Drug.findByDrugName(drug)?: new Drug(drugName: drug).save(flush:true)}

        /*** Rooms ***/
        [[roomNumber: 1]
                , [roomNumber: 2]
                , [roomNumber: 3]
                , [roomNumber: 4]
                , [roomNumber: 5]
                , [roomNumber: 6]
                , [roomNumber: 7]
                , [roomNumber: 8]
                , [roomNumber: 9]
                , [roomNumber: 10]
        ].eachWithIndex { data, idx ->

            def room = new Room(data)
            if (!room.validate()) {
                room.errors.allErrors.each {
                    println it
                }
            } else {
                room.save(flush: true)
            }

        }

        assert Room.count() == 10
    }

	def destroy = {
	}

	}


	/*Use code snippet to print errors on a specific object
	if (!user.validate()) {
		user.errors.allErrors.each {
			println it
		}
	}*/


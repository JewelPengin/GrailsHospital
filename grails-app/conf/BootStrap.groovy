// domain class imports
import com.centurylink.hospital.auth.Authority
import com.centurylink.hospital.auth.Person
import com.centurylink.hospital.auth.PersonAuthority
import com.centurylink.hospital.Doctor
import com.centurylink.hospital.Drug
import com.centurylink.hospital.Room
import com.centurylink.hospital.Prescription
import com.centurylink.hospital.Patient

// Atmosphere imports
import com.odelia.grails.plugins.atmosphere.StratosphereServlet
import com.centurylink.hospital.GenericAtmosphereHandler
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes

class BootStrap {

	def springSecurityService

	def init = { servletContext ->
		createData()

        // Should you need to dynamically inject atmosphere handlers
        /*def people = Person.list()
        def parentContext = servletContext.getAttribute(GrailsApplicationAttributes.APPLICATION_CONTEXT)
        def bb = new grails.spring.BeanBuilder(parentContext)

        def beanName = "atmosphereNotification"
        def beans = bb.beans {
            people.each { person ->
                "${beanName}${person.username}Service"(GenericAtmosphereHandler) {}
                "${beanName}${person.username}ServiceGrailsHandler"(com.odelia.grails.plugins.atmosphere.GrailsHandler) {
                    targetService = ref("${beanName}${person.username}Service")
                    servletContext = servletContext
                }
            }
        }
        beans.registerBeans(parentContext)
        def handlers = servletContext.getAttribute(StratosphereServlet.ATMOSPHERE_PLUGIN_SERVICE_HANDLERS)
        people.each { person ->
            handlers << [mapping: "/atmosphere/notification/${person.username}",
                         handler: parentContext.getBean("${beanName}${person.username}ServiceGrailsHandler")]
        }
        servletContext.setAttribute(StratosphereServlet.ATMOSPHERE_PLUGIN_SERVICE_HANDLERS, handlers)*/

	}

    def destroy = {

    }

    public void createData() {

        //login data
        def userRole = Authority.findByAuthority('ROLE_USER') ?: new Authority(authority: 'ROLE_USER').save(failOnError: true, flush:true)
        def adminRole = Authority.findByAuthority('ROLE_ADMIN')?: new Authority(authority: 'ROLE_ADMIN').save(failOnError: true, flush: true)

        [[username: 'dave', realname: 'Dave Gunawan', password: '1234', enabled: true, role: userRole]
                , [username: 'jonathan', realname: 'Jonathan Larson', password: '1234', enabled: true, role: adminRole]
                , [username: 'brad', realname: 'Bradley Rhoades', password: '1234', enabled: true, role: userRole]
                , [username: 'farouk', realname:'Farouk Althlathini', password: '1234', enabled: true, role: adminRole]
        ].eachWithIndex{ data, idx ->
            def user = Person.findByUsername(data.username)?: new Person(data).save(failOnError: true, flush: true)

            if (!user.authorities.contains(data.role)){
                PersonAuthority.create user, data.role, true
            }
        }

        assert Person.count() == 4
        assert Authority.count() == 2
        assert PersonAuthority.count() == 4

        //create some hospital data

        /****Doctors***/

        [[lastName: 'Damon', firstName: 'Matt']
                , [lastName: 'Pitt', firstName: 'Brad']
                , [lastName: 'Hopkins', firstName: 'Anthony']
                , [lastName: 'Gere', firstName: 'Richard']
                , [lastName: 'Clooney', firstName: 'George']
        ].each {data ->
            def cardiologist = Doctor.findByLastName(data.lastName) ?:
                new Doctor(data).save(failOnError:true, flush:true)
        }
        /*** Drugs***/
        ['advil', 'ibuprofen', 'nyquil', 'neosporin', 'centrum', 'fishoil'].each {drugname ->
            def druglist = Drug.findByDrugName(drugname)?: new Drug(drugName: drugname).save(failOnError:true, flush:true)}


        assert Doctor.count() == 5

        assert Drug.count() == 6


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
        ].each { data ->

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
}

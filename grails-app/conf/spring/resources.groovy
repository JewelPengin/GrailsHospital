// Place your Spring DSL code here
import com.centurylink.hospital.SuccessfulAuthListener
import com.centurylink.hospital.ApplicationContextHolder
import com.centurylink.hospital.GenericAtmosphereHandler

beans = {
	genericAtmosphereHandler(GenericAtmosphereHandler)
	applicationContextHolder(ApplicationContextHolder) { bean ->
		bean.factoryMethod = 'getInstance'
	}
	successfulAuthListener(SuccessfulAuthListener) {
        contextHolder = ref("applicationContextHolder")
	}
}

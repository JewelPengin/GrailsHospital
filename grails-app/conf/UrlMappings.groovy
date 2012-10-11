class UrlMappings {

	static mappings = {

		name ajax: "/ajax/$type/$artifact/$function" (controller: 'ajax', action: 'index') {
			constraints {
				type(matches: /controller|service/)
			}
		}

		"/$controller/$action?/$id?" {
			constraints {
				// apply constraints here
			}
		}

		"/" (view:"/index")
		"500" (view:'/error')
	}
}

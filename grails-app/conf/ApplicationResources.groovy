modules = {
	jquery {
		resource url: '/js/jquery-1.8.2.min.js', disposition: 'head'
		resource url: '/js/jquery.json-2.3.js', disposition: 'head'
	}

	jqueryUi {
		dependsOn 'jquery'
		resource url: '/js/jquery-ui-1.9.0.min.js', disposition: 'head'
		resource url: '/css/cupertino/jquery-ui-1.8.23.custom.css'
	}

	modernizr {
		resource url: '/js/modernizr.js', disposition: 'head'
	}

	cookie {
		dependsOn 'jquery'
		resource url: '/js/jquery.cookie.js', disposition: 'head'
	}

	core {
		dependsOn 'jquery', 'jqueryUi', 'modernizr', 'atmosphere'

		resource url: '/css/reset.less', attrs:[rel: "stylesheet/less", type:'css'], bundle:'bundle_core'
		resource url: '/css/main.less', attrs:[rel: "stylesheet/less", type:'css'], bundle:'bundle_core'
		resource url: '/css/ie7.less', attrs:[rel: "stylesheet/less", type:'css'],
			wrapper: { s -> "<!--[if lt IE 8]>$s<![endif]-->" }

		resource url: '/css/testing.css'

		resource url: '/js/toolbar.js'
	}

	grid {
		dependsOn 'core', 'cookie'
		resource url: '/js/date.js'
		resource url: '/js/grid.js'
	}
}
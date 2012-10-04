modules = {
    core {
        dependsOn 'jquery', 'jquery-ui', 'modernizr'

        resource url: '/css/reset.less', attrs:[rel: "stylesheet/less", type:'css'], bundle:'bundle_core'
        resource url: '/css/main.less', attrs:[rel: "stylesheet/less", type:'css'], bundle:'bundle_core'
        resource url: '/css/ie7.less', attrs:[rel: "stylesheet/less", type:'css'],
            wrapper: { s -> "<!--[if lt IE 8]>$s<![endif]-->" }

        resource url: '/css/testing.css'

        resource url: '/js/toolbar.js'
    }

    grid {
        dependsOn 'core'

        resource url: '/js/date.js'
        resource url: '/js/grid.js'
    }

    overrides {
        'jquery-theme' {
            resource id:'theme', url:'/css/cupertino/jquery-ui-1.8.23.custom.css'
        }
    }
}
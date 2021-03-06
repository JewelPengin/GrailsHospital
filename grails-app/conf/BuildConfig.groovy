grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.tomcat.nio=true

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        runtime 'mysql:mysql-connector-java:5.1.20'
        runtime 'org.apache.commons:commons-lang3:3.0'
    }

    plugins {
        runtime ":hibernate:$grailsVersion"
        runtime ":lesscss-resources:1.3.0.3"
        runtime ":resources:1.1.6"
        // TODO: All these break... figure out what's going on there.
        //runtime ":zipped-resources:1.0"
        //runtime ":yui-minify-resources:0.1.5"
        //runtime ":cached-resources:1.0"
        //runtime ":cache-headers:1.1.5"
        runtime ":spring-security-core:1.2.7.3"
        runtime ":mail:1.0"

        runtime ":database-migration:1.1"

        //build ":jetty:2.0.1" // Jetty 7.6.0
        build ":tomcat:$grailsVersion"

        compile ':cache:1.0.0'
        compile ":executor:0.3"
        //compile ":cometd:0.2.2"
        compile ":svn:1.0.2"
        compile ":atmosphere:0.4.2.1"
        compile ":activemq:0.4.1"
        compile ":jms:1.2"
    }
}

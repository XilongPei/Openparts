# openparts

install tomcat:
https://bitnami.com/stack/tomcat

startup mysql:
~/tomcatstack-8.5.16-0/mysql/scripts/ctl.sh start

build openparts:
~/openparts$ mvn clean compile -U

run openparts:
~/openparts/Openparts-web$ mvn jetty:run


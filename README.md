# openparts

install tomcat:
https://bitnami.com/stack/tomcat

startup mysql:
~/tomcatstack-8.5.16-0/mysql/scripts/ctl.sh start

startup redis:
xilong@xilong-OptiPlex-7010:$ redis-server
xilong@xilong-OptiPlex-7010:$ redis-cli
127.0.0.1:6379> ping
PONG

build openparts:
~/openparts$ mvn clean compile -U

run openparts:
~/openparts/Openparts-web$ mvn jetty:run


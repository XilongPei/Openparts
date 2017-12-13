# Openparts

install tomcat:
```sh
https://bitnami.com/stack/tomcat
```
startup mysql:
```sh
~/tomcatstack-8.5.16-0/mysql/scripts/ctl.sh start
```
startup redis:
```sh
xilong@xilong-OptiPlex-7010:$ redis-server
xilong@xilong-OptiPlex-7010:$ redis-cli
127.0.0.1:6379> ping
PONG
```
build openparts:
```sh
~/openparts$ mvn clean compile -U

or

~/openparts$ mvn install
```
run openparts:
```sh
~/openparts/Openparts-web$ mvn jetty:run
```

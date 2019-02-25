#!/bin/sh
echo "Packaging using Maven"
mvn package
echo "Deploying to mywb.vcs.net"
scp ./target/database-0.0.1-SNAPSHOT.jar supadmin@mywb.vcs.net:~/database.jar
echo "Deployment complete"
echo "Restarting myWB server"
ssh supadmin@mywb.vcs.net 'sudo /etc/init.d/database restart'

#!/bin/bash

export CLASSPATH=lib/jars/*:lib/bundles/*:build

SERVER_PORT="4004"
if [ "x$1" != "x" ]
then
        SERVER_PORT="$1"

fi


#CLIENT_JVMFLAGS="-agentlib:jdwp=transport=dt_socket,address=1235,server=y,suspend=n" 
bin/zkCli.sh -timeout 100000000 -server localhost:$SERVER_PORT -path /app1

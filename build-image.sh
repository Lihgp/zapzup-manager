#!/bin/bash

DOCKER_USERNAME=$1

docker-compose -f docker-compose.yml up -d
mvn clean install
docker-compose -f docker-compose.yml down
cd zapzup-manager-application
docker build -t "$DOCKER_USERNAME"/zapzup_manager:1.0 .
cd ../
docker push victorsantoss/zapzup_manager:1.0
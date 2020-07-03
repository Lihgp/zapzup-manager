#!/bin/bash

docker-compose -f docker-compose.yml up -d
mvn clean install
docker-compose -f docker-compose.yml down
cd zapzup-manager-application
docker build -t victorsantoss/zapzup_manager:1.0 .
cd ../
docker push victorsantoss/zapzup_manager:1.0